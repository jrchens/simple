package me.simple.cms.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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

import me.simple.admin.entity.Pagination;
import me.simple.admin.entity.Tag;
import me.simple.admin.entity.TagRowMapper;
import me.simple.cms.service.TagService;
import me.simple.util.Constants;
import me.simple.util.SqlUtil;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private static final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Tag saveTag(Tag tag) {
	logger.info("me.simple.admin.service.impl.TagServiceImpl.saveTag");
	if (checkTagExists(tag.getTagname())) {
	    throw new DuplicateKeyException(tag.getTagname());
	}
	String owner = (String) SecurityUtils.getSubject().getPrincipal();
	Timestamp now = new Timestamp(System.currentTimeMillis());
	jdbcTemplate.update("insert into cms_tag(tagname,viewname,deleted,owner,cruser,crtime) values(?,?,?,?,?,?)", 
		tag.getTagname(),
		tag.getViewname(), 
		false,
		owner,
		owner,
		now
		);
	return tag;
    }

    @Override
    public int removeTag(String tagname) {
	logger.info("me.simple.admin.service.impl.TagServiceImpl.removeTag");
	if (!checkTagExists(tagname)) {
	    throw new EmptyResultDataAccessException(tagname, 1);
	}

	String owner = (String) SecurityUtils.getSubject().getPrincipal();
	Timestamp now = new Timestamp(System.currentTimeMillis());
	
	int affected = jdbcTemplate.update("delete from cms_product_tag where tagname = ?", tagname);
	affected = jdbcTemplate.update("update cms_tag set deleted = ?,mduser = ?,mdtime = ? where tagname = ?", 
		true,
		owner,
		now,
		tagname);
	return affected;
    }

    @Override
    public int updateTag(Tag tag) {
	logger.info("me.simple.admin.service.impl.TagServiceImpl.updateTag");
	if (!checkTagExists(tag.getTagname())) {
	    throw new EmptyResultDataAccessException(tag.getTagname(), 1);
	}

	String owner = (String) SecurityUtils.getSubject().getPrincipal();
	Timestamp now = new Timestamp(System.currentTimeMillis());
	
	return jdbcTemplate.update("update cms_tag set viewname = ?, mduser = ?,mdtime = ? where tagname = ?", 
		tag.getViewname(),
		owner,
		now,
		tag.getTagname());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkTagExists(String tagname) {
	logger.info("me.simple.admin.service.impl.TagServiceImpl.existsTag");
	int count = jdbcTemplate.queryForObject("select count(1) from cms_tag where tagname = ?", Integer.class,
		tagname);

	// List<Tag> tagList =
	// jdbcTemplate.query("select * from cms_tag where tagname = ?", new
	// Object[]{tagname}, new TagRowMapper());
	// return tagList.isEmpty() ? false : true;

	return count > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Tag getTag(String tagname) {
	logger.info("me.simple.admin.service.impl.TagServiceImpl.getTag");
	List<Tag> tagList = jdbcTemplate.query("select * from cms_tag where deleted = ? and tagname = ?",
		new Object[] { false, tagname }, new TagRowMapper());

	if (tagList.isEmpty()) {
	    throw new EmptyResultDataAccessException(tagname, 1);
	}
	return tagList.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> queryTag(Tag tag, Pagination<Tag> pagination) {
	logger.info("me.simple.admin.service.impl.TagServiceImpl.queryTag");
	List<Object> args = Lists.newArrayList();
	StringBuffer querySql = new StringBuffer();
	
	querySql.append("select * from cms_tag where deleted = 0 ");
	Subject subject = SecurityUtils.getSubject();
	if(!subject.hasRole(Constants.ROLE_SYS_ADMIN)){
	    querySql.append("and owner = ? ");
	    args.add(subject.getPrincipal());
	}
	
	String viewname = tag.getViewname();
	if (StringUtils.hasText(viewname)) {
	    querySql.append("and (viewname like ? or tagname like ?) ");
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

	List<Tag> records = jdbcTemplate.query(querySql.toString(), new TagRowMapper(), args.toArray());
	pagination.setRecords(records);

	return records;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Tag> queryAll() {
	logger.info("me.simple.admin.service.impl.TagServiceImpl.queryAll");
	StringBuffer querySql = new StringBuffer();
	List<Object> args = Lists.newArrayList();
	
	querySql.append("select * from cms_tag where deleted = 0 ");
	Subject subject = SecurityUtils.getSubject();
	if(!subject.hasRole(Constants.ROLE_SYS_ADMIN)){
	    querySql.append("and owner = ? ");
	    args.add(subject.getPrincipal());
	}
	
	return jdbcTemplate.query(querySql.toString(), new TagRowMapper(), args.toArray());
    }
}
