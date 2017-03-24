package me.simple.cms.controller;

import java.util.List;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import me.simple.cms.service.ChannelService;
import me.simple.commons.entity.Channel;
import me.simple.commons.entity.ChannelNode;
import me.simple.commons.entity.Pagination;

@Controller
@RequestMapping(value = "cms/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;
     @Autowired
     private MessageSource messageSource;

    @RequestMapping(value = { "" }, method = RequestMethod.GET)
    public String index(Channel channel, Pagination<Channel> pagination, Model model) {
	/* BindingResult bindingResult, */
	channelService.queryChannel(channel, pagination);
	model.addAttribute("pagination", pagination);

	getChannelChildren(channel.getParentName(), model);

	model.addAttribute("channel", channel);

	return "cms/channel_index";
    }

    private void getChannelChildren(String channelName, Model model) {
	if (StringUtils.hasText(channelName)) {
	    Channel parentChannel = channelService.getChannel(channelName);
	    model.addAttribute("parentChannel", parentChannel);
	    model.addAttribute("parentChannelName", parentChannel.getChannelName());
	    model.addAttribute("parentChannelParentName", parentChannel.getParentName());
	    channelName = parentChannel.getParentName();
	}
	model.addAttribute("CHANNEL_CHILDREN", channelService.queryChannelChildren(channelName));
    }

    @RequestMapping(value = { "create" }, method = RequestMethod.GET)
    public String create(Channel channel, BindingResult bindingResult, Model model) {

	getChannelChildren(channel.getParentName(), model);

	if (!bindingResult.hasErrors()) {
	    channel.setParentName(channel.getParentName());
	    model.addAttribute(channel);
	}
	return "cms/channel_create";
    }

    @RequestMapping(value = { "save" }, method = RequestMethod.POST)
    public String save(@Validated(value = { Save.class }) Channel channel, BindingResult bindingResult, Model model) {
	if (!bindingResult.hasErrors()) {
	    channelService.saveChannel(channel);
	} else {
	    return "cms/channel_create";
	}
	return "redirect:/cms/channel?parentName=" + ObjectUtils.getDisplayString(channel.getParentName());
    }

    @RequestMapping(value = { "edit" }, method = RequestMethod.GET)
    public String edit(@Validated(value = { Get.class }) Channel channel, BindingResult bindingResult, Model model) {

	getChannelChildren(channel.getParentName(), model);

	if (!bindingResult.hasErrors()) {
	    model.addAttribute(channelService.getChannel(channel.getChannelName()));
	}
	return "cms/channel_edit";
    }

    @RequestMapping(value = { "update" }, method = RequestMethod.POST)
    public String update(@Validated(value = { Update.class }) Channel channel, BindingResult bindingResult,
	    Model model) {
	if (!bindingResult.hasErrors()) {
	    model.addAttribute(channelService.updateChannel(channel));
	} else {
	    return "cms/channel_edit";
	}
	return "redirect:/cms/channel?parentName=" + ObjectUtils.getDisplayString(channel.getParentName());
    }

    @RequestMapping(value = { "remove" }, method = RequestMethod.POST)
    public String remove(@Validated(value = { Remove.class }) Channel channel, BindingResult bindingResult,
	    Model model) {
	if (!bindingResult.hasErrors()) {
	    channelService.removeChannel(channel.getChannelName());
	}
	return "redirect:/cms/channel";
    }
    

    @RequestMapping(value = { "queryUserChannel" }, method = RequestMethod.GET)
    @ResponseBody
    public List<ChannelNode> queryUserChannel(String all,String owner, String channelName) {
	List<ChannelNode> channelNodeList = channelService.queryUserChannelTree(owner,channelName);
	if("true".equals(all)){
	    ChannelNode root = new ChannelNode();
	    root.setText(messageSource.getMessage("common.all", null, null));
	    channelNodeList.add(0, root);
	}
	return channelNodeList;
    }
    

    @RequestMapping(value = { "queryUserChannelChain" }, method = RequestMethod.GET)
//    @ResponseBody
    public List<ChannelNode> queryUserChannelChain(String all,String owner, String channelName) {
	List<ChannelNode> channelNodeList = channelService.queryUserChannelChain(owner,channelName);
	if("true".equals(all)){
	    ChannelNode root = new ChannelNode();
	    root.setText(messageSource.getMessage("common.idx", null, null));
	    channelNodeList.add(0, root);
	}
	return channelNodeList;
    }

}
