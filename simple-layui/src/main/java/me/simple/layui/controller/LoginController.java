package me.simple.layui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

@Controller
public class LoginController {

    @RequestMapping(value={"login"},method=RequestMethod.GET)    
    public String login(){
	return "login";
    }

    @RequestMapping(value={"login"},method=RequestMethod.POST)    
    public String login(String username,String password,String kaptcha,String rememberMe,WebRequest webRequest){
	webRequest.setAttribute("user", username, RequestAttributes.SCOPE_SESSION);
	return "redirect:/";
    }

    @RequestMapping(value={"logout"},method=RequestMethod.GET)    
    public String logout(){
	return "redirect:/login";
    }
}
