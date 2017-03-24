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

import me.simple.admin.service.RoleService;
import me.simple.commons.entity.Pagination;
import me.simple.commons.entity.Role;

@Controller
@RequestMapping(value = "admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
//    @Autowired
//    private MessageSource messageSource;

    @RequestMapping(value = { "" }, method = RequestMethod.GET)
    public String index(Role role, Pagination<Role> pagination, Model model) {
	/* BindingResult bindingResult, */
	roleService.queryRole(role, pagination);
	model.addAttribute("pagination", pagination);
	return "admin/role_index";
    }

    @RequestMapping(value = { "create" }, method = RequestMethod.GET)
    public String create(Role role, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(role);
	}
	return "admin/role_create";
    }

    @RequestMapping(value = { "save" }, method = RequestMethod.POST)
    public String save(@Validated(value = { Save.class }) Role role, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    roleService.saveRole(role);
	} else {
	    return "admin/role_create";
	}
	return "redirect:/admin/role";
    }

    @RequestMapping(value = { "edit" }, method = RequestMethod.GET)
    public String edit(@Validated(value = { Get.class }) Role role, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(roleService.getRole(role.getRolename()));
	}
	return "admin/role_edit";
    }

    @RequestMapping(value = { "update" }, method = RequestMethod.POST)
    public String update(@Validated(value = { Update.class }) Role role, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(roleService.updateRole(role));
	} else {
	    return "admin/role_edit";
	}
	return "redirect:/admin/role";
    }

    @RequestMapping(value = { "remove" }, method = RequestMethod.POST)
    public String remove(@Validated(value = { Remove.class }) Role role, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    roleService.removeRole(role.getRolename());
	}
	return "redirect:/admin/role";
    }

}
