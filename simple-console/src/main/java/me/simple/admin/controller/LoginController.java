package me.simple.admin.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import me.simple.admin.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserService userService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String login(WebRequest webRequest) {
	return "login";
    }

    @RequestMapping(value = { "login" }, method = RequestMethod.POST)
    public String login(WebRequest webRequest, String username, String password, String rememberMe) {
	/* , String kaptcha */
	Subject subject = null;
	try {

	    subject = SecurityUtils.getSubject();
	    subject.login(new UsernamePasswordToken(username, password, "on".equalsIgnoreCase(rememberMe)));

	    if (subject.isAuthenticated()) {
		webRequest.setAttribute("SYS_ALL_USERS", userService.queryAll(), RequestAttributes.SCOPE_SESSION);
		
		return "redirect:/admin/dashboard";
	    }
	} catch (UnknownAccountException e) {
	    webRequest.setAttribute("error", messageSource.getMessage("unknown.account", null, null),
		    RequestAttributes.SCOPE_SESSION);
	} catch (IncorrectCredentialsException e) {
	    webRequest.setAttribute("error", messageSource.getMessage("incorrect.credentials.account", null, null),
		    RequestAttributes.SCOPE_SESSION);
	} catch (Exception e) {
	    webRequest.setAttribute("error", messageSource.getMessage("server.error", null, null),
		    RequestAttributes.SCOPE_SESSION);
	}
	return "redirect:/";
    }

    @RequestMapping(value = { "logout" }, method = RequestMethod.GET)
    public String logout() {
	SecurityUtils.getSubject().logout();
	return "redirect:/";
    }

}
