package me.simple.admin.service.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import me.simple.admin.service.RoleService;
import me.simple.commons.entity.Pagination;
import me.simple.commons.entity.Role;
import me.simple.commons.entity.RoleRowMapper;
import me.simple.util.SqlUtil;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Role saveRole(Role role) {
	logger.info("me.simple.admin.service.impl.RoleServiceImpl.saveRole");
	if (checkRoleExists(role.getRolename())) {
	    throw new DuplicateKeyException(role.getRolename());
	}
	jdbcTemplate.update("insert into sys_role(rolename,viewname,deleted) values(?,?,?)", role.getRolename(),
		role.getViewname(), false);
	return role;
    }

    @Override
    public int removeRole(String rolename) {
	logger.info("me.simple.admin.service.impl.RoleServiceImpl.removeRole");
	if (!checkRoleExists(rolename)) {
	    throw new EmptyResultDataAccessException(rolename, 1);
	}
	int affected = jdbcTemplate.update("delete from sys_user_role where rolename = ?", rolename);
	// TODO delete sys_role_permission
	affected = jdbcTemplate.update("update sys_role set deleted = ? where rolename = ?", true, rolename);
	return affected;
    }

    @Override
    public int updateRole(Role role) {
	logger.info("me.simple.admin.service.impl.RoleServiceImpl.updateRole");
	if (!checkRoleExists(role.getRolename())) {
	    throw new EmptyResultDataAccessException(role.getRolename(), 1);
	}
	return jdbcTemplate.update("update sys_role set viewname = ? where rolename = ?", role.getViewname(),
		role.getRolename());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkRoleExists(String rolename) {
	logger.info("me.simple.admin.service.impl.RoleServiceImpl.existsRole");
	int count = jdbcTemplate.queryForObject("select count(1) from sys_role where rolename = ?", Integer.class,
		rolename);

	// List<Role> roleList =
	// jdbcTemplate.query("select * from sys_role where rolename = ?", new
	// Object[]{rolename}, new RoleRowMapper());
	// return roleList.isEmpty() ? false : true;

	return count > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRole(String rolename) {
	logger.info("me.simple.admin.service.impl.RoleServiceImpl.getRole");
	List<Role> roleList = jdbcTemplate.query("select * from sys_role where deleted = ? and rolename = ?",
		new Object[] { false, rolename }, new RoleRowMapper());

	if (roleList.isEmpty()) {
	    throw new EmptyResultDataAccessException(rolename, 1);
	}
	return roleList.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> queryRole(Role role, Pagination<Role> pagination) {
	logger.info("me.simple.admin.service.impl.RoleServiceImpl.queryRole");
	StringBuffer querySql = new StringBuffer();
	querySql.append("select * from sys_role where deleted = 0 ");

	List<Object> args = Lists.newArrayList();
	String viewname = role.getViewname();
	if (StringUtils.hasText(viewname)) {
	    querySql.append("and (viewname like ? or rolename like ?) ");
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

	List<Role> records = jdbcTemplate.query(querySql.toString(), new RoleRowMapper(), args.toArray());
	pagination.setRecords(records);

	return records;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Role> queryAll() {
	return jdbcTemplate.query("select * from sys_role where deleted = 0", new RoleRowMapper());
    }
}
