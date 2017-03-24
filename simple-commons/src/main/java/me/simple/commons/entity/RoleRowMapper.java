package me.simple.commons.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet res, int idx) throws SQLException {
	Role obj = new Role();
	obj.setRolename(res.getString("rolename"));
	obj.setViewname(res.getString("viewname"));
	return obj;
    }
    
}
