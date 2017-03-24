package me.simple.cache.service;

import java.util.List;
import java.util.Map;

import me.simple.commons.entity.Article;

public interface CacheService {

    String getString(String key);

    Map<String, String> getMap(String key);

    List<Map<String, Object>> queryMapList(String key);

    Article getArticle(String key);

    List<Article> queryArticleList(String key);
    
    void clean();

}
