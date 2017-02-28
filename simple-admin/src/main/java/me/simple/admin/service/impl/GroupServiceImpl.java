package me.simple.admin.service.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import me.simple.admin.entity.Group;
import me.simple.admin.entity.GroupRowMapper;
import me.simple.admin.entity.Pagination;
import me.simple.admin.service.GroupService;
import me.simple.util.SqlUtil;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Group saveGroup(Group group) {
	logger.info("me.simple.admin.service.impl.GroupServiceImpl.saveGroup");
	if (checkGroupExists(group.getGroupname())) {
	    throw new DuplicateKeyException(group.getGroupname());
	}
	jdbcTemplate.update("insert into sys_group(groupname,viewname,deleted) values(?,?,?)", group.getGroupname(),
		group.getViewname(), false);
	return group;
    }

    @Override
    public int removeGroup(String groupname) {
	logger.info("me.simple.admin.service.impl.GroupServiceImpl.removeGroup");
	if (!checkGroupExists(groupname)) {
	    throw new EmptyResultDataAccessException(groupname, 1);
	}
	// TODO verify sys_user (group field)
	int count = jdbcTemplate.queryForObject("select count(1) from sys_user where deleted = 0 and groupname = ?", Integer.class,
		groupname);
	if(count > 0){
	    throw new DataIntegrityViolationException(groupname);
	}
	
	int affected = jdbcTemplate.update("delete from sys_user_group where groupname = ?", groupname);
	affected = jdbcTemplate.update("update sys_group set deleted = ? where groupname = ?", true, groupname);
	return affected;
    }

    @Override
    public int updateGroup(Group group) {
	logger.info("me.simple.admin.service.impl.GroupServiceImpl.updateGroup");
	if (!checkGroupExists(group.getGroupname())) {
	    throw new EmptyResultDataAccessException(group.getGroupname(), 1);
	}
	return jdbcTemplate.update("update sys_group set viewname = ? where groupname = ?", group.getViewname(),
		group.getGroupname());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkGroupExists(String groupname) {
	logger.info("me.simple.admin.service.impl.GroupServiceImpl.existsGroup");
	int count = jdbcTemplate.queryForObject("select count(1) from sys_group where groupname = ?", Integer.class,
		groupname);

	// List<Group> groupList =
	// jdbcTemplate.query("select * from sys_group where groupname = ?", new
	// Object[]{groupname}, new GroupRowMapper());
	// return groupList.isEmpty() ? false : true;

	return count > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Group getGroup(String groupname) {
	logger.info("me.simple.admin.service.impl.GroupServiceImpl.getGroup");
	List<Group> groupList = jdbcTemplate.query("select * from sys_group where deleted = ? and groupname = ?",
		new Object[] { false, groupname }, new GroupRowMapper());

	if (groupList.isEmpty()) {
	    throw new EmptyResultDataAccessException(groupname, 1);
	}
	return groupList.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> queryGroup(Group group, Pagination<Group> pagination) {
	logger.info("me.simple.admin.service.impl.GroupServiceImpl.queryGroup");
	StringBuffer querySql = new StringBuffer();
	querySql.append("select * from sys_group where deleted = 0 ");

	List<Object> args = Lists.newArrayList();
	String viewname = group.getViewname();
	if (StringUtils.hasText(viewname)) {
	    querySql.append("and (viewname like ? or groupname like ?) ");
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

	List<Group> records = jdbcTemplate.query(querySql.toString(), new GroupRowMapper(), args.toArray());
	pagination.setRecords(records);

	return records;
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Group> queryAll() {
	return jdbcTemplate.query("select * from sys_group where deleted = 0", new GroupRowMapper(), new Object[]{});
    }

}
