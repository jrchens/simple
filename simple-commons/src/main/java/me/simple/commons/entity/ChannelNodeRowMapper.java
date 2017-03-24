package me.simple.commons.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ChannelNodeRowMapper implements RowMapper<ChannelNode> {

    @Override
    public ChannelNode mapRow(ResultSet res, int idx) throws SQLException {
	ChannelNode obj = new ChannelNode();
	obj.setText(res.getString("viewname"));
	obj.setName(res.getString("channel_name"));
	obj.setParentName(res.getString("parent_name"));
	obj.setHref(res.getString("url"));
	return obj;
    }

}
