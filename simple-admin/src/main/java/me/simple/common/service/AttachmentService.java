package me.simple.common.service;

import java.util.List;
import java.util.Map;

import me.simple.admin.entity.Attachment;

public interface AttachmentService {
    Attachment saveAttachment(Attachment attachment);
    int removeAttachment(String refId);
    List<Map<String,Object>> queryAttachment(Attachment attachment);
}