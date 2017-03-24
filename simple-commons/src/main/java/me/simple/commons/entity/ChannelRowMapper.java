package me.simple.commons.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ChannelRowMapper implements RowMapper<Channel> {

    @Override
    public Channel mapRow(ResultSet res, int idx) throws SQLException {
	Channel obj = new Channel();
	obj.setChannelName(res.getString("channel_name"));
	obj.setParentName(res.getString("parent_name"));
	obj.setViewname(res.getString("viewname"));
	obj.setUrl(res.getString("url"));
	
	obj.setOwner(res.getString("owner"));
	obj.setCruser(res.getString("cruser"));
	obj.setCrtime(res.getTimestamp("crtime"));
	obj.setMduser(res.getString("mduser"));
	obj.setMdtime(res.getTimestamp("mdtime"));
	return obj;
    }
    
}
