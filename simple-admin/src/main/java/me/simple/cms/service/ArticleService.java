package me.simple.cms.service;

import java.util.List;

import me.simple.admin.entity.Pagination;
import me.simple.admin.entity.Article;

public interface ArticleService {

    Article saveArticle(Article article);

    int removeArticle(String articleId);

    /**
     * just update article title,intro,origin,richContent,author,pubDate
     * 
     * @param article
     * @return
     */
    int updateArticle(Article article);

    boolean checkArticleExists(String articleId);

    Article getArticle(String articleId);

    /**
     * query by title
     * 
     * @param article
     * @param pagination
     * @return
     */
    List<Article> queryArticle(Article article, final Pagination<Article> pagination);
    
}