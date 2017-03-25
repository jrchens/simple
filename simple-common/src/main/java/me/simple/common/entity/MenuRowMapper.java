package me.simple.common.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MenuRowMapper implements RowMapper<Menu> {

    @Override
    public Menu mapRow(ResultSet res, int idx) throws SQLException {
	Menu obj = new Menu();
	obj.setMenuname(res.getString("menuname"));
	obj.setMenuUrl(res.getString("menu_url"));
	obj.setViewname(res.getString("viewname"));
	obj.setGrp(res.getInt("grp"));
	obj.setSrt(res.getInt("srt"));
	return obj;
    }
    
}
