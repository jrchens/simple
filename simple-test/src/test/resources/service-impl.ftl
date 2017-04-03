package ${package}.service.impl;

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

import ${package}.service.${beaname}Service;
import me.simple.entity.${beaname};
import me.simple.util.SqlUtil;

@Service
@Transactional
public class ${beaname}ServiceImpl implements ${beaname}Service {
    private static final Logger logger = LoggerFactory.getLogger(${beaname}Service.class);
    
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String delete_sql = "update ${tablename} set deleted = 1 , mduser = :mduser , mdtime = :mdtime where ${PK.name} = :${PK.name}";
    private String update_sql = "update ${tablename} set ${updateFields} mduser = :mduser , mdtime = :mdtime where ${PK.name} = :${PK.name}";
    private String select_sql = "select * from ${tablename} where deleted = 0 ";
    // "row_id","name","price","pub_date","deleted","disabled","row_status","cruser","crtime","mduser","mdtime"

    @Autowired
    public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
    this.simpleJdbcInsert.setTableName("${tablename}");
    }

    @Override
    public int save(${beaname} ${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}) {
      return simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}));
    }

    @Override
    public int delete(final ${beaname} ${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}) {
      return namedParameterJdbcTemplate.update(delete_sql, new BeanPropertySqlParameterSource(${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}));
    }

    @Override
    public int update(${beaname} ${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}) {
    return namedParameterJdbcTemplate.update(update_sql, new BeanPropertySqlParameterSource(${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}));
    }

    @Override
    @Transactional(readOnly=true)
    public ${beaname} get(Long ${PK.name}) {
      ${beaname} ${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)} = new ${beaname}(${PK.name});
      return namedParameterJdbcTemplate.queryForObject(select_sql + "and ${PK.name} = :${PK.name}", 
        new BeanPropertySqlParameterSource(${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}),
        new BeanPropertyRowMapper<${beaname}>(${beaname}.class));
    }
    
    private void queryCondition(${beaname} ${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)},final StringBuffer querySql , final List<Object> args){
#foreach( ${queryField} in ${queryFields} )
      String ${queryField.name} = ${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}.get${queryField.name.substring(0,1).toUpperCase()}${queryField.name.substring(1)}();
      if(StringUtils.hasText(${queryField.name})){
          querySql.append("and ${queryField.name} like ? ");
          args.add(SqlUtil.like(${queryField.name}));
      }
#end
    }

    @Override
    @Transactional(readOnly=true)
    public List<${beaname}> query(${beaname} ${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}) {

    String srt = ${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}.getSrt();
    String ord = ${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}.getOrd();
    Integer page = ${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}.getPage();
    Integer rows = ${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}.getRows();

    List<Object> args = Lists.newArrayList();
    StringBuffer querySql = new StringBuffer(select_sql);
    
    queryCondition(${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)},querySql,args);

    String countSql = SqlUtil.countSql(querySql.toString());
    int total = jdbcTemplate.queryForObject(countSql, args.toArray(), Integer.class);
    ${beaname.substring(0,1).toLowerCase()}${beaname.substring(1)}.setTotal(total);

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

    return jdbcTemplate.query(querySql.toString(), args.toArray(), new BeanPropertyRowMapper<${beaname}>(${beaname}.class));

    }

}