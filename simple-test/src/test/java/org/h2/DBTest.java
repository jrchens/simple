package org.h2;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-embedded-database-test.xml")
public class DBTest {

    private JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(DBTest.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Test
    public void testQueryForList() {
	List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from users");
	for (Map<String, Object> map : list) {
	    logger.info("{}",map);
	}
    }

}
