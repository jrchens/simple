package me.simple.admin.service.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.simple.admin.service.MenuService;
import me.simple.common.entity.Menu;
import me.simple.common.entity.MenuRowMapper;

@Service
@Transactional(readOnly=true)
public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Override
    public String getMenuUrl(String menuname) {
	logger.info("me.simple.admin.service.impl.MenuServiceImpl.getMenuUrl");
	List<Menu> menuList = jdbcTemplate.query("select * from sys_menu where deleted = 0 and menuname = ?",new Object[]{menuname},new MenuRowMapper());
	if(menuList.isEmpty()){
	    throw new EmptyResultDataAccessException(menuname, 1);
	}
	return menuList.get(0).getMenuUrl();
    }

    @Override
    public List<Menu> queryAll() {
	logger.info("me.simple.admin.service.impl.MenuServiceImpl.queryAll");
	return jdbcTemplate.query("select * from sys_menu where deleted = 0 order by srt asc",new Object[]{},new MenuRowMapper());
    }

}
