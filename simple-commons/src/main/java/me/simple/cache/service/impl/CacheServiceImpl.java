package me.simple.cache.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.simple.cache.service.CacheService;
import me.simple.commons.entity.Article;
import me.simple.commons.entity.ArticleRowMapper;

@Service
@Transactional(readOnly=true)
public class CacheServiceImpl implements CacheService {

    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    
    @Override
    @Cacheable(cacheNames={"defaults"})
    public String getString(String key) {
	logger.info("getString by key : {}",key);
	return UUID.randomUUID().toString();
    }

    @Override
    @Cacheable(cacheNames={"maps"})
    public Map<String, String> getMap(String key) {
	logger.info("getMap by key : {}",key);
	return System.getenv();
    }
    
    @Override
    @Cacheable(cacheNames={"mapList"})
    public List<Map<String, Object>> queryMapList(String key) {
	logger.info("queryMapList by key : {}",key);
	return jdbcTemplate.queryForList("select * from cms_article");
    }
    
    @Override
    @Cacheable(cacheNames={"articles"})
    public Article getArticle(String key) {
	logger.info("getArticle by key : {}",key);
	return jdbcTemplate.queryForObject("select * from cms_article limit 1",new ArticleRowMapper());
    }

    @Override
    @Cacheable(cacheNames={"articleList"})
    public List<Article> queryArticleList(String key) {
	logger.info("queryArticleList by key : {}",key);
	return jdbcTemplate.query("select * from cms_article",new ArticleRowMapper());
    }

    @Override
    @CacheEvict(allEntries=true,cacheNames={"defaults","maps","mapList","articles","articleList"})
    public void clean() {
    }

}
