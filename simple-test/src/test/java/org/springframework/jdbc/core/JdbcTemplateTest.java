package org.springframework.jdbc.core;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class JdbcTemplateTest {

    private JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(JdbcTemplateTest.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void testQueryForObject() {
	String uuid = jdbcTemplate.queryForObject("select uuid()", String.class);
	logger.debug("uuid: {}",uuid);
	Assert.isTrue(uuid.length() == 36);
    }
    @Test
    public void testShowVariables() {
	List<Map<String,Object>> variables = jdbcTemplate.queryForList("show variables like '%character%'");
	for (Map<String, Object> map : variables) {
//	    Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
//	    while(iterator.hasNext()){
//		Entry<String, Object> entry = iterator.next();
//		logger.info("{} : {}",entry.getKey(),entry.getValue());
//	    }
	    logger.info("{} : {}",map.get("Variable_name"),map.get("Value"));
	}
    }
}
