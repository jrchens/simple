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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import me.simple.cms.service.ProductService;
import me.simple.cms.service.TagService;
import me.simple.common.entity.Pagination;
import me.simple.common.entity.Product;

@Controller
@RequestMapping(value = "cms/product")
public class ProductController {

    @Autowired
    private ProductService productService; 
    @Autowired
    private TagService tagService; 
//    @Autowired
//    private MessageSource messageSource;

    @RequestMapping(value = { "" }, method = RequestMethod.GET)
    public String index(WebRequest webRequest, Product product, Pagination<Product> pagination, Model model) {
	/* BindingResult bindingResult, */
	productService.queryProduct(product, pagination);
	model.addAttribute("pagination", pagination);
	webRequest.setAttribute("CMS_ALL_TAGS", tagService.queryAll(),RequestAttributes.SCOPE_SESSION);
	return "cms/product_index";
    }

    @RequestMapping(value = { "create" }, method = RequestMethod.GET)
    public String create(Product product, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(product);
	}
	return "cms/product_create";
    }

    @RequestMapping(value = { "save" }, method = RequestMethod.POST)
    public String save(@Validated(value = { Save.class }) Product product, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    productService.saveProduct(product);
	} else {
	    return "cms/product_create";
	}
	return "redirect:/cms/product/edit?productId="+product.getProductId();
    }

    @RequestMapping(value = { "edit" }, method = RequestMethod.GET)
    public String edit(@Validated(value = { Get.class }) Product product, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    Product obj = productService.getProduct(product.getProductId());
	    obj.setTags(productService.queryProductags(product.getProductId()));
	    model.addAttribute(obj);
	}
	return "cms/product_edit";
    }

    @RequestMapping(value = { "update" }, method = RequestMethod.POST)
    public String update(@Validated(value = { Update.class }) Product product, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(productService.updateProduct(product));
	} else {
	    return "cms/product_edit";
	}
	return "redirect:/cms/product";
    }

    @RequestMapping(value = { "remove" }, method = RequestMethod.POST)
    public String remove(@Validated(value = { Remove.class }) Product product, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    productService.removeProduct(product.getProductId());
	}
	return "redirect:/cms/product";
    }

}
