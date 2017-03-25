package me.simple.common.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class GroupRowMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet res, int idx) throws SQLException {
	Group obj = new Group();
	obj.setGroupname(res.getString("groupname"));
	obj.setViewname(res.getString("viewname"));
	return obj;
    }
    
}
