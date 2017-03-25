package me.simple.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import me.simple.admin.service.MenuService;

@Controller
@RequestMapping(value="admin")
public class IndexController {

    @Autowired
    private MenuService menuService;
    
    @RequestMapping(value={"dashboard"},method=RequestMethod.GET)
    public String dashboard(WebRequest webRequest){
	if(ObjectUtils.isEmpty(webRequest.getAttribute("MENU_RECORDS", RequestAttributes.SCOPE_SESSION)))
	 webRequest.setAttribute("MENU_RECORDS", menuService.queryAll(), RequestAttributes.SCOPE_SESSION);
	return "admin/dashboard";
    }
    
    @RequestMapping(value = { "menu/{menuname}" }, method = RequestMethod.GET)
    public String index(WebRequest webRequest, @PathVariable("menuname") String menuname) {
	String menuUrl = menuService.getMenuUrl(menuname);	
	webRequest.setAttribute("CURRENT_MENUNAME", menuname, RequestAttributes.SCOPE_SESSION);
	// TODO verify file exists
	webRequest.setAttribute("MODULE_JS_FILE_NAME", menuname.replace(':', '-'), RequestAttributes.SCOPE_SESSION);
	return "redirect:"+menuUrl;
    }

}
