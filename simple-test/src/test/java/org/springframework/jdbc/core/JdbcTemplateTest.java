package org.springframework.jdbc.core;

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
}
