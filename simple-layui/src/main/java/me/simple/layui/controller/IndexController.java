package me.simple.layui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value={""},method=RequestMethod.GET)    
    public String index(){
	return "index";
    }
    

    @RequestMapping(value={"register"},method=RequestMethod.GET)    
    public String register(){
	return "register";
    }
    
    @RequestMapping(value={"register"},method=RequestMethod.POST)    
    public String register(String username,String password,String kaptcha){
	return "redirect:/";
    }
}
