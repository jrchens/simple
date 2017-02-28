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

import me.simple.admin.entity.Pagination;
import me.simple.admin.entity.Tag;
import me.simple.cms.service.TagService;

@Controller
@RequestMapping(value = "cms/tag")
public class TagController {

    @Autowired
    private TagService tagService;
//    @Autowired
//    private MessageSource messageSource;

    @RequestMapping(value = { "" }, method = RequestMethod.GET)
    public String index(Tag tag, Pagination<Tag> pagination, Model model) {
	/* BindingResult bindingResult, */
	tagService.queryTag(tag, pagination);
	model.addAttribute("pagination", pagination);
	return "cms/tag_index";
    }

    @RequestMapping(value = { "create" }, method = RequestMethod.GET)
    public String create(Tag tag, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(tag);
	}
	return "cms/tag_create";
    }

    @RequestMapping(value = { "save" }, method = RequestMethod.POST)
    public String save(@Validated(value = { Save.class }) Tag tag, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    tagService.saveTag(tag);
	} else {
	    return "cms/tag_create";
	}
	return "redirect:/cms/tag";
    }

    @RequestMapping(value = { "edit" }, method = RequestMethod.GET)
    public String edit(@Validated(value = { Get.class }) Tag tag, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(tagService.getTag(tag.getTagname()));
	}
	return "cms/tag_edit";
    }

    @RequestMapping(value = { "update" }, method = RequestMethod.POST)
    public String update(@Validated(value = { Update.class }) Tag tag, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(tagService.updateTag(tag));
	} else {
	    return "cms/tag_edit";
	}
	return "redirect:/cms/tag";
    }

    @RequestMapping(value = { "remove" }, method = RequestMethod.POST)
    public String remove(@Validated(value = { Remove.class }) Tag tag, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    tagService.removeTag(tag.getTagname());
	}
	return "redirect:/cms/tag";
    }

}
