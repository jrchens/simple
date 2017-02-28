package me.simple.common.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import me.simple.admin.entity.Attachment;
import me.simple.common.service.AttachmentService;

@Controller
@RequestMapping(value = "common/attach")
public class AttachmentController {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(WebRequest webRequest,
	    // @Validated(value={Save.class})
	    Attachment attachment, BindingResult bindingResult) {
	logger.info("me.simple.common.controller.AttachmentController.upload");
	if (!bindingResult.hasErrors()) {
	    attachmentService.saveAttachment(attachment);
	}
	return query(webRequest, attachment);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> delete(String key) {
	logger.info("me.simple.common.controller.AttachmentController.delete");
	Map<String, String> result = Maps.newHashMap();
	int affected = attachmentService.removeAttachment(key);
	result.put("success", 1 == affected ? "true" : "false");
	return result;
    }

    @RequestMapping(value = "query", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> query(WebRequest webRequest, Attachment attachment) {
	logger.info("me.simple.common.controller.AttachmentController.query");
	String contextPath = webRequest.getContextPath();
	Map<String, Object> result = Maps.newHashMap();
	List<Map<String, Object>> list = attachmentService.queryAttachment(attachment);
	List<String> initialPreview = Lists.newArrayList();
	List<Map<String, Object>> initialPreviewConfig = Lists.newArrayList();
	Map<String, String> extra = Maps.newHashMap();
	String thumbnail = attachment.getThumbnail();
	for (Map<String, Object> map : list) {
	    String url = ObjectUtils.getDisplayString(map.get("url"));
	    if (StringUtils.hasText(thumbnail)) {
		String[] thumbnails = thumbnail.split(";");
		for (String _thumbnail : thumbnails) {
		    if (url.indexOf(_thumbnail) != -1) {
			initialPreview.add(url);
			map.put("url", contextPath + "/common/attach/delete");
			extra.put("refId", ObjectUtils.getDisplayString(map.get("key")));
			map.put("extra", extra);
			initialPreviewConfig.add(map);
		    }
		}
	    } else {
		initialPreview.add(url);
		map.put("url", contextPath + "/common/attach/delete");
		extra.put("refId", ObjectUtils.getDisplayString(map.get("key")));
		map.put("extra", extra);
		initialPreviewConfig.add(map);
	    }
	}
	// result.put("error", error);
	result.put("append", true);
	result.put("initialPreview", initialPreview);
	result.put("initialPreviewConfig", initialPreviewConfig);
	return result;
    }

}
