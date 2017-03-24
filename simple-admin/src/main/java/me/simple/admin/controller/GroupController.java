package me.simple.admin.controller;

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

import me.simple.admin.service.GroupService;
import me.simple.common.entity.Group;
import me.simple.common.entity.Pagination;

@Controller
@RequestMapping(value = "admin/group")
public class GroupController {

    @Autowired
    private GroupService groupService;
//    @Autowired
//    private MessageSource messageSource;

    @RequestMapping(value = { "" }, method = RequestMethod.GET)
    public String index(Group group, Pagination<Group> pagination, Model model) {
	/* BindingResult bindingResult, */
	groupService.queryGroup(group, pagination);
	model.addAttribute("pagination", pagination);
	return "admin/group_index";
    }

    @RequestMapping(value = { "create" }, method = RequestMethod.GET)
    public String create(Group group, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(group);
	}
	return "admin/group_create";
    }

    @RequestMapping(value = { "save" }, method = RequestMethod.POST)
    public String save(@Validated(value = { Save.class }) Group group, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    groupService.saveGroup(group);
	} else {
	    return "admin/group_create";
	}
	return "redirect:/admin/group";
    }

    @RequestMapping(value = { "edit" }, method = RequestMethod.GET)
    public String edit(@Validated(value = { Get.class }) Group group, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(groupService.getGroup(group.getGroupname()));
	}
	return "admin/group_edit";
    }

    @RequestMapping(value = { "update" }, method = RequestMethod.POST)
    public String update(@Validated(value = { Update.class }) Group group, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(groupService.updateGroup(group));
	} else {
	    return "admin/group_edit";
	}
	return "redirect:/admin/group";
    }

    @RequestMapping(value = { "remove" }, method = RequestMethod.POST)
    public String remove(@Validated(value = { Remove.class }) Group group, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    groupService.removeGroup(group.getGroupname());
	}
	return "redirect:/admin/group";
    }

}
