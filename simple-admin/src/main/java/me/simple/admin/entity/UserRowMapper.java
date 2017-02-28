package me.simple.admin.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet res, int idx) throws SQLException {
	User obj = new User();
	obj.setUsername(res.getString("username"));
	obj.setViewname(res.getString("viewname"));
	obj.setGroupname(res.getString("groupname"));
	return obj;
    }
    
}
