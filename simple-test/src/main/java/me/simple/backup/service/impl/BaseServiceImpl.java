package me.simple.backup.service.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import me.simple.backup.service.BaseService;
import me.simple.entity.Base;
import me.simple.util.SqlUtil;

@Service
@Transactional
public class BaseServiceImpl implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);
    
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String table_name = "sys_base";
    private String delete_sql = "update " + table_name
	    + " set deleted = :deleted , mduser = :mduser , mdtime = :mdtime where row_id = :rowId";
    private String update_sql = "update " + table_name
	    + " set name = :name, price = :price, pub_date = :pubDate, disabled = :disabled, row_status = :rowStatus , mduser = :mduser , mdtime = :mdtime where row_id = :rowId";
    private String select_sql = "select * from " + table_name + " where row_id = :rowId";
    // "row_id","name","price","pub_date","deleted","disabled","row_status","cruser","crtime","mduser","mdtime"
    private List<String> simpleJdbcInsertColumnNames = Lists.newArrayList("name", "price", "pub_date", "deleted",
	    "disabled", "row_status", "cruser", "crtime");

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

	this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
	this.simpleJdbcInsert.setTableName(table_name);
	this.simpleJdbcInsert.setColumnNames(simpleJdbcInsertColumnNames);
    }

    @Override
    public int save(Base base) {
	return simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(base));
    }

    @Override
    public int delete(Base base) {
	base.setDeleted(true);
	return namedParameterJdbcTemplate.update(delete_sql, new BeanPropertySqlParameterSource(base));
    }

    @Override
    public int update(Base base) {
	return namedParameterJdbcTemplate.update(update_sql, new BeanPropertySqlParameterSource(base));
    }

    @Override
    @Transactional(readOnly=true)
    public Base get(Long rowId) {
	Base base = new Base(rowId);
	return namedParameterJdbcTemplate.queryForObject(select_sql, new BeanPropertySqlParameterSource(base),
		new BeanPropertyRowMapper<Base>(Base.class));
    }

    @Override
    @Transactional(readOnly=true)
    public List<Base> query(Base base) {

	String name = base.getName();
	String srt = base.getSrt();
	String ord = base.getOrd();
	Integer page = base.getPage();
	Integer rows = base.getRows();

	List<Object> args = Lists.newArrayList();
	StringBuffer querySql = new StringBuffer();
	querySql.append("select * from ").append(table_name).append(" ");
	querySql.append("where deleted = ? ");
	args.add(false);

	if (StringUtils.hasText(name)) {
	    querySql.append("and name like ? ");
	    args.add(SqlUtil.like(name));
	}

	String countSql = SqlUtil.countSql(querySql.toString());
	int total = jdbcTemplate.queryForObject(countSql, args.toArray(), Integer.class);
	base.setTotal(total);

	// TODO check valid
	if (StringUtils.hasText(srt)) {
	    querySql.append("order by ? ");
	    args.add(srt);
	    if (StringUtils.hasText(ord)) {
		querySql.append("? ");
		args.add(ord);
	    }
	}
	querySql.append("limit ?,? ");
	args.add((page.intValue() - 1) * rows);
	args.add(rows);

	return jdbcTemplate.query(querySql.toString(), args.toArray(), new BeanPropertyRowMapper<Base>(Base.class));

    }

}
