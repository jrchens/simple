package me.simple.admin.service;

import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import me.simple.common.entity.Pagination;
import me.simple.common.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class UserServiceTest {

    @Autowired
    private UserService userService;
    private JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void resetUserPassword() {
	String password = "123456";
	int affected = jdbcTemplate.update("update sys_user set password = ? where deleted = 0", password);
	logger.info("affect sys_user rows: {}", affected);

	Pagination<User> pagination = new Pagination<User>();
	pagination.setPageSize(1000);
	List<User> userList = userService.queryUser(new User(), pagination);

	for (User user : userList) {
	    SecretKey key = new SecretKeySpec(user.getUsername().getBytes(Charsets.UTF_8), "HmacSHA512");
	    String cipherText = Hashing.hmacSha512(key).hashBytes(password.getBytes(Charsets.UTF_8)).toString();
	    jdbcTemplate.update("update sys_user set password = ? where username = ?", cipherText, user.getUsername());
	}
    }
    
    

    @Test
    public void testResetUserPassword() {

	Pagination<User> pagination = new Pagination<User>();
	pagination.setPageSize(1);
	List<User> userList = userService.queryUser(new User(), pagination);

	for (User user : userList) {
	    String password = userService.resetUserPassword(user.getUsername());
	    logger.info("username: {}, password: {}",user.getUsername(),password);
	}
    }
}
