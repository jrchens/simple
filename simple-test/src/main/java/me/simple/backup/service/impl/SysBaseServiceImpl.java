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

import me.simple.backup.service.SysBaseService;
import me.simple.entity.SysBase;
import me.simple.util.SqlUtil;

@Service
@Transactional
public class SysBaseServiceImpl implements SysBaseService {
    private static final Logger logger = LoggerFactory.getLogger(SysBaseService.class);
    
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String delete_sql = "update sys_base set deleted = 1 , mduser = :mduser , mdtime = :mdtime where row_id = :row_id";
    private String update_sql = "update sys_base set name = :name, price = :price, pub_date = :pub_date, disabled = :disabled, row_status = :row_status , mduser = :mduser , mdtime = :mdtime where row_id = :row_id";
    private String select_sql = "select * from sys_base where deleted = 0 ";
    // "row_id","name","price","pub_date","deleted","disabled","row_status","cruser","crtime","mduser","mdtime"

    @Autowired
    public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
    this.simpleJdbcInsert.setTableName("sys_base");
    }

    @Override
    public int save(SysBase sysBase) {
      return simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(sysBase));
    }

    @Override
    public int delete(final SysBase sysBase) {
      return namedParameterJdbcTemplate.update(delete_sql, new BeanPropertySqlParameterSource(sysBase));
    }

    @Override
    public int update(SysBase sysBase) {
    return namedParameterJdbcTemplate.update(update_sql, new BeanPropertySqlParameterSource(sysBase));
    }

    @Override
    @Transactional(readOnly=true)
    public SysBase get(Long row_id) {
      SysBase sysBase = new SysBase(row_id);
      return namedParameterJdbcTemplate.queryForObject(select_sql + "and row_id = :row_id", 
        new BeanPropertySqlParameterSource(sysBase),
        new BeanPropertyRowMapper<SysBase>(SysBase.class));
    }
    
    private void queryCondition(SysBase sysBase,final StringBuffer querySql , final List<Object> args){
      String name = sysBase.getName();
      if(StringUtils.hasText(name)){
          querySql.append("and name like ? ");
          args.add(SqlUtil.like(name));
      }
    }

    @Override
    @Transactional(readOnly=true)
    public List<SysBase> query(SysBase sysBase) {

    String srt = sysBase.getSrt();
    String ord = sysBase.getOrd();
    Integer page = sysBase.getPage();
    Integer rows = sysBase.getRows();

    List<Object> args = Lists.newArrayList();
    StringBuffer querySql = new StringBuffer(select_sql);
    
    queryCondition(sysBase,querySql,args);

    String countSql = SqlUtil.countSql(querySql.toString());
    int total = jdbcTemplate.queryForObject(countSql, args.toArray(), Integer.class);
    sysBase.setTotal(total);

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

    return jdbcTemplate.query(querySql.toString(), args.toArray(), new BeanPropertyRowMapper<SysBase>(SysBase.class));

    }

}