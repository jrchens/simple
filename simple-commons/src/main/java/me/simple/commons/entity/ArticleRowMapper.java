package me.simple.commons.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArticleRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet res, int idx) throws SQLException {
	Article obj = new Article();
	obj.setArticleId(res.getString("article_id"));
	obj.setChannelName(res.getString("channel_name"));
	obj.setTitle(res.getString("title"));
	obj.setIntro(res.getString("intro"));
	obj.setOrigin(res.getString("origin"));
	obj.setRichContent(res.getString("rich_content"));
	obj.setAuthor(res.getString("author"));
	obj.setPubDate(res.getDate("pub_date"));
	obj.setViewCount(res.getInt("view_count"));
	
	obj.setDisabled(res.getBoolean("disabled"));
	obj.setCruser(res.getString("cruser"));
	obj.setCrtime(res.getTimestamp("crtime"));
	obj.setMduser(res.getString("mduser"));
	obj.setMdtime(res.getTimestamp("mdtime"));
	return obj;
    }
    
}
