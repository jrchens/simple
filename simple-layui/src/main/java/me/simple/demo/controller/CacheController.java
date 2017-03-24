package me.simple.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.simple.cache.service.CacheService;
import me.simple.commons.entity.Article;

@Controller
@RequestMapping(value = "admin/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @RequestMapping(value = { "defaults" }, method = RequestMethod.GET)
    @ResponseBody
    public String defaults(@RequestParam(defaultValue = "1", required = false) String key) {
	return cacheService.getString(key);
    }

    @RequestMapping(value = { "maps" }, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> maps(@RequestParam(defaultValue = "1", required = false) String key) {
	return cacheService.getMap(key);
    }

    @RequestMapping(value = { "mapList" }, method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> mapList(@RequestParam(defaultValue = "1", required = false) String key) {
	return cacheService.queryMapList(key);
    }

    @RequestMapping(value = { "articles" }, method = RequestMethod.GET)
    @ResponseBody
    public Article articles(@RequestParam(defaultValue = "1", required = false) String key) {
	return cacheService.getArticle(key);
    }

    @RequestMapping(value = { "articleList" }, method = RequestMethod.GET)
    @ResponseBody
    public List<Article> articleList(@RequestParam(defaultValue = "1", required = false) String key) {
	return cacheService.queryArticleList(key);
    }
    
    @RequestMapping(value = { "clean" }, method = RequestMethod.GET)
    @ResponseBody
    public void clean() {
	cacheService.clean();
    }
}
