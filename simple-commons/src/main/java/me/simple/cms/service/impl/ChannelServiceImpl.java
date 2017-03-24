package me.simple.cms.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import me.simple.cms.service.ChannelService;
import me.simple.common.entity.Channel;
import me.simple.common.entity.ChannelNode;
import me.simple.common.entity.ChannelNodeRowMapper;
import me.simple.common.entity.ChannelRowMapper;
import me.simple.common.entity.Pagination;
import me.simple.util.Constants;
import me.simple.util.SqlUtil;

@Service
@Transactional
public class ChannelServiceImpl implements ChannelService {

    private static final Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final String[] columnNames = { "channel_name", "parent_name", "viewname", "owner", "url", "cruser",
	    "crtime" };

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
	this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
	this.simpleJdbcInsert.withTableName("cms_channel");
	this.simpleJdbcInsert.usingColumns(columnNames);
    }

    @Override
    public Channel saveChannel(Channel channel) {
	logger.info("me.simple.admin.service.impl.ChannelServiceImpl.saveChannel");
	String channelName = channel.getChannelName();
	if (checkChannelExists(channelName)) {
	    throw new DuplicateKeyException(channelName);
	}

	String owner = (String) SecurityUtils.getSubject().getPrincipal();
	Timestamp now = new Timestamp(System.currentTimeMillis());
	channel.setOwner(owner);
	channel.setCruser(owner);
	channel.setCrtime(now);
	if (!StringUtils.hasText(channel.getParentName())) {
	    channel.setParentName(null);
	}

	simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(channel));
	return channel;
    }

    @Override
    public int removeChannel(String channelName) {
	logger.info("me.simple.admin.service.impl.ChannelServiceImpl.removeChannel");
	if (!checkChannelExists(channelName)) {
	    throw new EmptyResultDataAccessException(channelName, 1);
	}
	int count = jdbcTemplate.queryForObject(
		"select count(1) from cms_article where deleted = 0 and channel_name = ?", Integer.class, channelName);
	if (count > 0) {
	    throw new DataIntegrityViolationException(channelName);
	}
	count = jdbcTemplate.queryForObject("select count(1) from cms_channel where deleted = 0 and parent_name = ?",
		Integer.class, channelName);
	if (count > 0) {
	    throw new DataIntegrityViolationException(channelName);
	}

	String owner = (String) SecurityUtils.getSubject().getPrincipal();
	Timestamp now = new Timestamp(System.currentTimeMillis());

	int affected = jdbcTemplate.update(
		"update cms_channel set deleted = ?, mduser = ?, mdtime = ? where channel_name = ?", true, owner, now,
		channelName);
	return affected;
    }

    @Override
    public int updateChannel(Channel channel) {
	logger.info("me.simple.admin.service.impl.ChannelServiceImpl.updateChannel");
	String channelName = channel.getChannelName();
	if (!checkChannelExists(channelName)) {
	    throw new EmptyResultDataAccessException(channelName, 1);
	}

	String url = channel.getUrl();
	String owner = (String) SecurityUtils.getSubject().getPrincipal();
	Timestamp now = new Timestamp(System.currentTimeMillis());
	if (StringUtils.hasText(channel.getOwner())) {
	    owner = channel.getOwner();
	}
	return jdbcTemplate.update(
		"update cms_channel set viewname = ?, owner = ?, url = ?, mduser = ?, mdtime = ? where channel_name = ?",
		channel.getViewname(), owner, owner, url, now, channelName);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkChannelExists(String channelName) {
	logger.info("me.simple.admin.service.impl.ChannelServiceImpl.existsChannel");
	StringBuffer querySql = new StringBuffer();
	querySql.append("select count(1) from cms_channel where deleted = 0 ");

	List<Object> args = Lists.newArrayList();
	Subject subject = SecurityUtils.getSubject();
	if (!subject.hasRole(Constants.ROLE_SYS_ADMIN)) {
	    querySql.append("and owner = ? ");
	    args.add(subject.getPrincipal());
	}

	querySql.append("and channel_name = ? ");
	args.add(channelName);

	int count = jdbcTemplate.queryForObject(querySql.toString(), Integer.class, args.toArray());

	return count > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Channel getChannel(String channelName) {
	logger.info("me.simple.admin.service.impl.ChannelServiceImpl.getChannel");

	StringBuffer querySql = new StringBuffer();
	querySql.append("select * from cms_channel where deleted = 0 ");

	List<Object> args = Lists.newArrayList();
	Subject subject = SecurityUtils.getSubject();
	if (!subject.hasRole(Constants.ROLE_SYS_ADMIN)) {
	    querySql.append("and owner = ? ");
	    args.add(subject.getPrincipal());
	}

	querySql.append("and channel_name = ? ");
	args.add(channelName);

	List<Channel> channelList = jdbcTemplate.query(querySql.toString(), args.toArray(), new ChannelRowMapper());

	if (channelList.isEmpty()) {
	    throw new EmptyResultDataAccessException(channelName, 1);
	}
	return channelList.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Channel> queryChannel(Channel channel, Pagination<Channel> pagination) {
	logger.info("me.simple.admin.service.impl.ChannelServiceImpl.queryChannel");
	StringBuffer querySql = new StringBuffer();
	querySql.append("select * from cms_channel where deleted = 0 ");
	List<Object> args = Lists.newArrayList();
	String parentName = channel.getParentName();
	logger.info("parentName {}", parentName);
	if (StringUtils.hasText(parentName)) {
	    parentName = getChannel(parentName).getChannelName();
	}

	Subject subject = SecurityUtils.getSubject();

	if (!subject.hasRole(Constants.ROLE_SYS_ADMIN)) {
	    querySql.append("and owner = ? ");
	    args.add(subject.getPrincipal());
	}

	if (StringUtils.hasText(parentName)) {
	    querySql.append("and parent_name = ? ");
	    args.add(parentName);
	} else {
	    querySql.append("and parent_name is null ");
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

	List<Channel> records = jdbcTemplate.query(querySql.toString(), new ChannelRowMapper(), args.toArray());
	pagination.setRecords(records);

	return records;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Channel> queryChannelChildren(String channelName) {

	logger.info("me.simple.admin.service.impl.ChannelServiceImpl.queryChannelChildren");
	StringBuffer querySql = new StringBuffer();
	querySql.append("select * from cms_channel where deleted = 0 ");

	List<Object> args = Lists.newArrayList();
	Subject subject = SecurityUtils.getSubject();

	if (!subject.hasRole(Constants.ROLE_SYS_ADMIN)) {
	    querySql.append("and owner = ? ");
	    args.add(subject.getPrincipal());
	}

	if (StringUtils.hasText(channelName)) {
	    querySql.append("and parent_name = ? ");
	    args.add(channelName);
	} else {
	    querySql.append("and parent_name is null ");
	}
	List<Channel> records = jdbcTemplate.query(querySql.toString(), new ChannelRowMapper(), args.toArray());
	return records;
    }

    @Transactional(readOnly = true)
    private void queryUserChannelChain(String owner, String channelName, List<ChannelNode> list) {
	logger.info("me.simple.admin.service.impl.ChannelServiceImpl.queryUserChannelChain");
	List<ChannelNode> _list = jdbcTemplate.query(
		"select * from cms_channel where deleted = 0 and owner = ? and channel_name = ?",
		new Object[] { owner, channelName }, new ChannelNodeRowMapper());
	for (ChannelNode channel : _list) {
	    list.add(channel);
	    queryUserChannelChain(owner, channel.getParentName(), list);
	}
    }

    @Transactional(readOnly = true)
    public List<ChannelNode> queryUserChannelChain(String owner, String channelName) {
	logger.info("me.simple.admin.service.impl.ChannelServiceImpl.queryChannelChain");
	
	Assert.hasLength(channelName/*, "channel_name::must.not.blank"*/);
	
	List<Object> args = Lists.newArrayList();
	
	StringBuffer querySql = new StringBuffer();
	querySql.append("select * from cms_channel where deleted = 0 and owner = ? ");

	if (!StringUtils.hasText(owner)) {
	    owner = (String) SecurityUtils.getSubject().getPrincipal();
	}
	args.add(owner);

	if (!StringUtils.hasText(channelName)) {
	    querySql.append("and channel_name is null ");
	} else {
	    querySql.append("and channel_name = ? ");
	    args.add(channelName);
	}
	
	List<ChannelNode> channelist = jdbcTemplate.query(
		querySql.toString(),
		args.toArray(), new ChannelNodeRowMapper());
	List<ChannelNode> channelChainList = Lists.newArrayList();
	channelChainList.addAll(channelist);
	
	for (ChannelNode channel : channelist) {
	    queryUserChannelChain(owner, channel.getParentName(), channelChainList);
	}
	
	return Lists.reverse(channelChainList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChannelNode> queryUserChannelTree(String owner, String channelName) {
	logger.info("me.simple.admin.service.impl.ChannelServiceImpl.queryUserChannelTree");

	List<Object> args = Lists.newArrayList();
	StringBuffer querySql = new StringBuffer();
	querySql.append("select * from cms_channel where deleted = 0 and owner = ? ");

	if (!StringUtils.hasText(owner)) {
	    owner = (String) SecurityUtils.getSubject().getPrincipal();
	}
	args.add(owner);

	if (!StringUtils.hasText(channelName)) {
	    querySql.append("and parent_name is null ");
	} else {
	    querySql.append("and parent_name = ? ");
	    args.add(channelName);
	}
	
	List<ChannelNode> list = jdbcTemplate.query(querySql.toString(), args.toArray(),
		new ChannelNodeRowMapper());
	for (ChannelNode channel : list) {
	    queryUserChannelTree(owner, channel.getName(), channel.getNodes());
	}
	return list;
    }

//    @Transactional(readOnly = true)
//    private boolean hasChild(String owner, String channelName) {
//	logger.info("me.simple.admin.service.impl.ChannelServiceImpl.hasChild");
//	int count = jdbcTemplate.queryForObject(
//		"select count(1) from cms_channel where deleted = 0 and parent_name = ?", Integer.class, channelName);
//	return count > 0;
//    }

    @Transactional(readOnly = true)
    private void queryUserChannelTree(String owner, String channelName, List<ChannelNode> list) {
	logger.info("me.simple.admin.service.impl.ChannelServiceImpl.queryUserChannelTree");
	List<ChannelNode> _list = jdbcTemplate.query(
		"select * from cms_channel where deleted = 0 and owner = ? and parent_name = ?",
		new Object[] { owner, channelName }, new ChannelNodeRowMapper());
	for (ChannelNode channel : _list) {
	    list.add(channel);
	    // if (hasChild(owner, channel.getText())) {
	    queryUserChannelTree(owner, channel.getName(), channel.getNodes());
	    // }
	}
    }

}
