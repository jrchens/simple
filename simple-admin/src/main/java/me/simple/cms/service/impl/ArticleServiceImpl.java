package me.simple.cms.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import me.simple.admin.entity.Pagination;
import me.simple.admin.entity.Article;
import me.simple.admin.entity.ArticleRowMapper;
import me.simple.cms.service.ArticleService;
import me.simple.cms.service.ChannelService;
import me.simple.util.Constants;
import me.simple.util.SqlUtil;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final String tableName = "cms_article";
    private static final String[] columnNames = { "article_id", "channel_name", "title", "intro", "origin",
	    "rich_content", "author", "pub_date", "view_count", "owner", "cruser", "crtime" };

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
	this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
	this.simpleJdbcInsert.withTableName(tableName);
	this.simpleJdbcInsert.usingColumns(columnNames);
    }
    
    @Autowired
    private ChannelService channelService; 

    @Override
    public Article saveArticle(Article article) {
	logger.info("me.simple.admin.service.impl.ArticleServiceImpl.saveArticle");
//	long articleId = article.getArticleId();
//	if (checkArticleExists(articleId)) {
//	    throw new DuplicateKeyException(String.valueOf(articleId));
//	}
	String owner = (String) SecurityUtils.getSubject().getPrincipal();
	Timestamp now = new Timestamp(System.currentTimeMillis());
	article.setOwner(owner);
	article.setCruser(owner);
	article.setCrtime(now);
	article.setArticleId(SqlUtil.uuid());
	
	simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(article));
	
	return article;
    }

    @Override
    public int removeArticle(String articleId) {
	logger.info("me.simple.admin.service.impl.ArticleServiceImpl.removeArticle");
	if (!checkArticleExists(articleId)) {
	    throw new EmptyResultDataAccessException(String.valueOf(articleId), 1);
	}

	String owner = (String) SecurityUtils.getSubject().getPrincipal();
	Timestamp now = new Timestamp(System.currentTimeMillis());
	
	int affected = jdbcTemplate.update("update cms_article set deleted = ?,mduser = ?,mdtime = ? where article_id = ?", 
		true,
		owner,
		now,
		articleId);
	return affected;
    }

    @Override
    public int updateArticle(Article article) {
	logger.info("me.simple.admin.service.impl.ArticleServiceImpl.updateArticle");
	String articleId = article.getArticleId();
	if (!checkArticleExists(articleId)) {
	    throw new EmptyResultDataAccessException(String.valueOf(articleId), 1);
	}

	String owner = (String) SecurityUtils.getSubject().getPrincipal();
	Timestamp now = new Timestamp(System.currentTimeMillis());
	// title,intro,origin,richContent,author,pubDate
	return jdbcTemplate.update("update cms_article set title = ? ,intro = ? ,origin = ? ,rich_content = ?, author = ?, pub_date = ?, mduser = ?,mdtime = ? where article_id = ?", 
		article.getTitle(),
		article.getIntro(), 
		article.getOrigin(),
		article.getRichContent(),
		article.getAuthor(),
		article.getPubDate(),
		owner,
		now,
		articleId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkArticleExists(String articleId) {
	logger.info("me.simple.admin.service.impl.ArticleServiceImpl.checkArticleExists");
	StringBuffer querySql = new StringBuffer();
	querySql.append("select count(1) from cms_article where deleted = 0 ");

	List<Object> args = Lists.newArrayList();
	Subject subject = SecurityUtils.getSubject();
	if(!subject.hasRole(Constants.ROLE_SYS_ADMIN)){
	    querySql.append("and owner = ? ");
	    args.add(subject.getPrincipal());
	}
	querySql.append("and article_id = ? ");
	args.add(articleId);
	
	int count = jdbcTemplate.queryForObject(querySql.toString(), Integer.class,
		args.toArray());

	return count > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Article getArticle(String articleId) {
	logger.info("me.simple.admin.service.impl.ArticleServiceImpl.getArticle");
	StringBuffer querySql = new StringBuffer();
	querySql.append("select * from cms_article where deleted = 0 ");

	List<Object> args = Lists.newArrayList();
	Subject subject = SecurityUtils.getSubject();
	if(!subject.hasRole(Constants.ROLE_SYS_ADMIN)){
	    querySql.append("and owner = ? ");
	    args.add(subject.getPrincipal());
	}
	querySql.append("and article_id = ? ");
	args.add(articleId);
	
	List<Article> articleList = jdbcTemplate.query(querySql.toString(),
		args.toArray(), new ArticleRowMapper());

	if (articleList.isEmpty()) {
	    throw new EmptyResultDataAccessException(String.valueOf(articleId), 1);
	}
	
	Article article = articleList.get(0);
	String channelNameValue = channelService.getChannel(article.getChannelName()).getViewname();
	article.setChannelNameValue(channelNameValue);
	return article;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> queryArticle(Article article, Pagination<Article> pagination) {
	logger.info("me.simple.admin.service.impl.ArticleServiceImpl.queryArticle");
	StringBuffer querySql = new StringBuffer();
	querySql.append("select * from cms_article where deleted = 0 ");

	List<Object> args = Lists.newArrayList();
	Subject subject = SecurityUtils.getSubject();
	if(!subject.hasRole(Constants.ROLE_SYS_ADMIN)){
	    querySql.append("and owner = ? ");
	    args.add(subject.getPrincipal());
	}
	
	String title = article.getTitle();
	if (StringUtils.hasText(title)) {
	    querySql.append("and title like ? ");
	    args.add(SqlUtil.like(title));
	}
	
	String channelName = article.getChannelName();
	if (StringUtils.hasText(channelName)) {
	    querySql.append("and channel_name = ? ");
	    args.add(channelName);
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

	List<Article> records = jdbcTemplate.query(querySql.toString(), new ArticleRowMapper(), args.toArray());
	pagination.setRecords(records);
	
	System.out.println(String.format("total page : %d", pagination.getTotalPage()));

	return records;
    }

}
