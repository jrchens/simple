package me.simple.cms.controller;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import me.simple.cms.service.ArticleService;
import me.simple.common.entity.Article;
import me.simple.common.entity.Pagination;

@Controller
@RequestMapping(value = "cms/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService; 
//    @Autowired
//    private MessageSource messageSource;

    @RequestMapping(value = { "" }, method = RequestMethod.GET)
    public String index(WebRequest webRequest, Article article, Pagination<Article> pagination, Model model) {
	/* BindingResult bindingResult, */
	articleService.queryArticle(article, pagination);
	model.addAttribute("pagination", pagination);
	System.out.println(String.format("total page : %d", pagination.getTotalPage()));
	
	return "cms/article_index";
    }

    @RequestMapping(value = { "create" }, method = RequestMethod.GET)
    public String create(Article article, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    article.setPubDate(new java.sql.Date(System.currentTimeMillis()));
	    model.addAttribute(article);
	}
	return "cms/article_create";
    }

    @RequestMapping(value = { "save" }, method = RequestMethod.POST)
    public String save(@Validated(value = { Save.class }) Article article, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    articleService.saveArticle(article);
	} else {
	    return "cms/article_create";
	}
	return "redirect:/cms/article/edit?articleId="+article.getArticleId();
    }

    @RequestMapping(value = { "edit" }, method = RequestMethod.GET)
    public String edit(@Validated(value = { Get.class }) Article article, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    Article obj = articleService.getArticle(article.getArticleId());
	    model.addAttribute(obj);
	}
	return "cms/article_edit";
    }

    @RequestMapping(value = { "update" }, method = RequestMethod.POST)
    public String update(@Validated(value = { Update.class }) Article article, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(articleService.updateArticle(article));
	} else {
	    return "cms/article_edit";
	}
	return "redirect:/cms/article";
    }

    @RequestMapping(value = { "remove" }, method = RequestMethod.POST)
    public String remove(@Validated(value = { Remove.class }) Article article, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    articleService.removeArticle(article.getArticleId());
	}
	return "redirect:/cms/article";
    }

}
