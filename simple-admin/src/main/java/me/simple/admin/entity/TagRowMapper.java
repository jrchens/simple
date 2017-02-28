package me.simple.admin.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TagRowMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet res, int idx) throws SQLException {
	Tag obj = new Tag();
	obj.setTagname(res.getString("tagname"));
	obj.setViewname(res.getString("viewname"));
	obj.setOwner(res.getString("owner"));
	obj.setCruser(res.getString("cruser"));
	obj.setCrtime(res.getTimestamp("crtime"));
	obj.setMduser(res.getString("mduser"));
	obj.setMdtime(res.getTimestamp("mdtime"));
	return obj;
    }
    
}
