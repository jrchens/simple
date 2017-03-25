package me.simple.admin.service.impl;

import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

import me.simple.admin.service.UserService;
import me.simple.common.entity.Pagination;
import me.simple.common.entity.User;
import me.simple.common.entity.UserRowMapper;
import me.simple.util.SqlUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User saveUser(User user) {
	logger.info("me.simple.admin.service.impl.UserServiceImpl.saveUser");
	if(checkUserExists(user.getUsername())){
	    throw new DuplicateKeyException(user.getUsername());
	}
	jdbcTemplate.update("insert into sys_user(username,viewname,groupname,password,deleted) values(?,?,?,?,?)",
		user.getUsername(), 
		user.getViewname(), 
		user.getGroupname(),
		user.getPassword(), false);
	return user;
    }

    @Override
    public int removeUser(String username) {
	logger.info("me.simple.admin.service.impl.UserServiceImpl.removeUser");
	if(!checkUserExists(username)){
	    throw new EmptyResultDataAccessException(username,1);
	}
	int aff = jdbcTemplate.update("delete from sys_user_role where username = ?", username);
	aff = jdbcTemplate.update("update sys_user set deleted = ? where username = ?", true, username);
	return aff;
    }

    @Override
    public int updateUser(User user) {
	logger.info("me.simple.admin.service.impl.UserServiceImpl.updateUser");
	
	String username = user.getUsername();
	if (!checkUserExists(user.getUsername())) {
	    throw new EmptyResultDataAccessException(username, 1);
	}

	jdbcTemplate.update("delete from sys_user_role where username = ?", username);
	List<String> roles = user.getRoles();

	List<Object[]> batchArgs = Lists.newArrayList();
	for (String role : roles) {
	    batchArgs.add(new Object[] {username, role });
	}
	jdbcTemplate.batchUpdate("insert into sys_user_role(username,rolename) values(?,?)", batchArgs);

	return jdbcTemplate.update("update sys_user set viewname = ?, groupname = ? where username = ?",
		user.getViewname(), user.getGroupname(), username);
    }

    @Override
    @Transactional(readOnly=true)
    public boolean checkUserExists(String username) {
	logger.info("me.simple.admin.service.impl.UserServiceImpl.existsUser");
	int count = jdbcTemplate.queryForObject("select count(1) from sys_user where username = ?", Integer.class,
		username);
	
//	List<User> userList = 
//	jdbcTemplate.query("select * from sys_user where username = ?", new Object[]{username}, new UserRowMapper());
//	return userList.isEmpty() ? false : true;
	
	return count > 0;
    }

    @Override
    @Transactional(readOnly=true)
    public User getUser(String username) {
	logger.info("me.simple.admin.service.impl.UserServiceImpl.getUser");
	List<User> userList = jdbcTemplate.query("select * from sys_user where deleted = ? and username = ?",
		new Object[] { false, username }, new UserRowMapper());

	if (userList.isEmpty()) {
	    throw new EmptyResultDataAccessException(username, 1);
	}
	return userList.get(0);
    }

    @Override
    @Transactional(readOnly=true)
    public List<User> queryUser(User user, Pagination<User> pagination) {
	logger.info("me.simple.admin.service.impl.UserServiceImpl.queryUser");
	StringBuffer querySql = new StringBuffer();
	querySql.append("select * from sys_user where deleted = 0 ");

	List<Object> args = Lists.newArrayList();
	String viewname = user.getViewname();
	if (StringUtils.hasText(viewname)) {
	    querySql.append("and (viewname like ? or username like ?) ");
	    args.add(SqlUtil.like(viewname));
	    args.add(SqlUtil.like(viewname));
	}

	int total = jdbcTemplate.queryForObject(SqlUtil.countSql(querySql.toString()), Integer.class, args.toArray());
	pagination.setTotalRecord(total);

	String order = pagination.getOrder();
	String sort = pagination.getSort();
	if (StringUtils.hasText(order)) {
	    querySql.append("order by ").append(SqlUtil.getOrder(order)).append(" ");
	    if (StringUtils.hasText(sort)) {
		querySql.append(SqlUtil.getOrder(sort)).append(" ");
	    }
	}
	querySql.append("limit ?,?");
	args.add(pagination.getOffset());
	args.add(pagination.getPageSize());
	
	List<User> records = jdbcTemplate.query(querySql.toString(), new UserRowMapper(), args.toArray());
	pagination.setRecords(records);
	
	return records;
    }

    @Override
    @Transactional(readOnly=true)
    public List<String> queryUserRoles(String username) {
	logger.info("me.simple.admin.service.impl.UserServiceImpl.queryUserRoles");
	return jdbcTemplate.queryForList("select rolename from sys_user_role where username = ?", String.class, username);
    }
    
    @Override
    public String resetUserPassword(String username) {
	logger.info("me.simple.admin.service.impl.UserServiceImpl.resetUserPassword");
	if(!checkUserExists(username)){
	    throw new EmptyResultDataAccessException(username,1);
	}
	String password = Hashing.crc32().hashLong(System.currentTimeMillis()).toString();

	SecretKey key = new SecretKeySpec(username.getBytes(Charsets.UTF_8), "HmacSHA512");
	String cipherText = Hashing.hmacSha512(key).hashBytes(password.getBytes(Charsets.UTF_8)).toString();
	jdbcTemplate.update("update sys_user set password = ? where username = ?", cipherText,username);
	return password;
    }
    
    
    @Override
    public List<User> queryAll() {
	logger.info("me.simple.admin.service.impl.UserServiceImpl.queryAll");
	return jdbcTemplate.query("select * from sys_user where deleted = 0 ", new UserRowMapper());
	
    }
}
