package me.simple.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BaseRowMapper implements RowMapper<Base> {

    @Override
    public Base mapRow(ResultSet rs, int rowNum) throws SQLException {
	Base obj = new Base();
	obj.setRowId(rs.getObject("rowId",Long.class));
	obj.setName(rs.getString("name"));
	obj.setPrice(rs.getObject("price",Double.class));
	obj.setPubDate(rs.getDate("pubDate"));
	
	obj.setDeleted(rs.getObject("deleted",Boolean.class));
	obj.setDisabled(rs.getObject("disabled",Boolean.class));
	obj.setRowStatus(rs.getObject("rowStatus",Integer.class));
	obj.setCruser(rs.getString("cruser"));
	obj.setCrtime(rs.getTimestamp("crtime"));
	obj.setMduser(rs.getString("mduser"));
	obj.setMdtime(rs.getTimestamp("mdtime"));
	return obj;
    }

}
