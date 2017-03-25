package me.simple.admin.controller;

import java.util.Locale;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import me.simple.admin.service.GroupService;
import me.simple.admin.service.RoleService;
import me.simple.admin.service.UserService;
import me.simple.common.entity.Pagination;
import me.simple.common.entity.User;
import me.simple.util.Constants;

@Controller
@RequestMapping(value = "admin/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = { "" }, method = RequestMethod.GET)
    public String index(WebRequest webRequest, User user, Pagination<User> pagination, Model model) {
	/* BindingResult bindingResult, */
	webRequest.setAttribute("ADMIN_USER_GROUPS", groupService.queryAll(), RequestAttributes.SCOPE_SESSION);
	webRequest.setAttribute("ADMIN_USER_ROLES", roleService.queryAll(), RequestAttributes.SCOPE_SESSION);
	
	userService.queryUser(user, pagination);
	model.addAttribute("pagination", pagination);
	return "admin/user_index";
    }

    @RequestMapping(value = { "create" }, method = RequestMethod.GET)
    public String create(User user, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(user);
	}
	return "admin/user_create";
    }

    @RequestMapping(value = { "save" }, method = RequestMethod.POST)
    public String save(@Validated(value = { Save.class }) User user, BindingResult bindingResult, Model model) {
	// check groupname
	String groupname = user.getGroupname();
	if(!groupService.checkGroupExists(groupname)){
	    bindingResult.rejectValue("groupname", "invalid.field.value", new Object[]{groupname}, "");
	}
	if (!bindingResult.hasErrors()) {
	    userService.saveUser(user);
	} else {
	    return "admin/user_create";
	}
	return "redirect:/admin/user";
//	return "redirect:/admin/user?viewname="+user.getUsername();
    }

    @RequestMapping(value = { "edit" }, method = RequestMethod.GET)
    public String edit(@Validated(value = { Get.class }) User user, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(userService.getUser(user.getUsername()));
	    model.addAttribute("USER_ROLES",userService.queryUserRoles(user.getUsername()));
	}
	return "admin/user_edit";
    }

    @RequestMapping(value = { "update" }, method = RequestMethod.POST)
    public String update(@Validated(value = { Update.class }) User user, BindingResult bindingResult, Model model) {
	String groupname = user.getGroupname();
	if(!groupService.checkGroupExists(groupname)){
	    bindingResult.rejectValue("groupname", "invalid.field.value", new Object[]{groupname}, "");
	}
	
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(userService.updateUser(user));
	} else {
	    return "admin/user_edit";
	}
	return "redirect:/admin/user";
	// return "redirect:/admin/user?viewname="+user.getUsername();
    }

    @RequestMapping(value = { "remove" }, method = RequestMethod.POST)
    public String remove(@Validated(value = { Remove.class }) User user, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    userService.removeUser(user.getUsername());
	}
	return "redirect:/admin/user";
    }
    


    @RequestMapping(value = { "resetPassword" }, method = RequestMethod.POST)
    public String resetPassword(WebRequest webRequest, @Validated(value = { Get.class }) User user, BindingResult bindingResult, Model model) {
	String password = null;
	if (!bindingResult.hasErrors()) {
	    password = userService.resetUserPassword(user.getUsername());
	    String message = messageSource.getMessage("admin.user.reset.password.success", new Object[]{password}, Locale.getDefault());
	    webRequest.setAttribute(Constants.SUCCESS_MESSAGE, message, RequestAttributes.SCOPE_SESSION);
	}
	return "redirect:/admin/user";
    }

}
