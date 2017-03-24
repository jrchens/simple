package me.simple.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.management.Query;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

public class Attachment implements Serializable {
//    @NotEmpty(groups={Save.class})
    private MultipartFile[] files;
    @Length(groups={Save.class,Query.class},min=1,max=32)
    private String module;
    @Length(groups={Save.class,Query.class},min=1,max=32)
    private String entity;
    @Length(groups={Save.class,Query.class},min=0,max=32)
    private String attr;
    @Range(groups={Remove.class},min=1,max=Integer.MAX_VALUE)
    private String refId;
    private List<String> refIds;
    private String thumbnail;
    

    private String attachId; // bigint(20) unsigned NOT NULL AUTOINCREMENT,
    private String originalAttachId; // bigint(20) DEFAULT NULL COMMENT '原始文件编号;被引用文件编号',
    private String attachOriginalName; // varchar(128) DEFAULT NULL COMMENT '原始文件名',
    private String attachPath; // varchar(128) DEFAULT NULL COMMENT '保存路径',
    private String attachName; // varchar(64) DEFAULT NULL COMMENT '文件名',
    private long attachSize; // int(11) DEFAULT NULL COMMENT '文件大小',
    private String attachType; // varchar(32) DEFAULT NULL COMMENT '文件类型',
    private String attachDesc; // varchar(64) DEFAULT NULL COMMENT '文件说明',
    private String attachSha1; // varchar(40) DEFAULT NULL COMMENT '文件SHA1摘要值',
    private int imageWidth; // int(11) DEFAULT NULL COMMENT '图片宽度',
    private int imageHeight; // int(11) DEFAULT NULL COMMENT '图片高度',
    private String cruser; // varchar(32) DEFAULT NULL COMMENT '创建者',
    private Timestamp crtime; // datetime DEFAULT NULL COMMENT '创建时间',
    private String mduser; // varchar(32) DEFAULT NULL COMMENT '修改者',
    private Timestamp mdtime; // datetime DEFAULT NULL COMMENT '修改用户',
    public String getAttachId() {
        return attachId;
    }
    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }
    public String getOriginalAttachId() {
        return originalAttachId;
    }
    public void setOriginalAttachId(String originalAttachId) {
        this.originalAttachId = originalAttachId;
    }
    public String getAttachOriginalName() {
        return attachOriginalName;
    }
    public void setAttachOriginalName(String attachOriginalName) {
        this.attachOriginalName = attachOriginalName;
    }
    public String getAttachPath() {
        return attachPath;
    }
    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
    }
    public String getAttachName() {
        return attachName;
    }
    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }
    public long getAttachSize() {
        return attachSize;
    }
    public void setAttachSize(long attachSize) {
        this.attachSize = attachSize;
    }
    public String getAttachType() {
        return attachType;
    }
    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }
    public String getAttachDesc() {
        return attachDesc;
    }
    public void setAttachDesc(String attachDesc) {
        this.attachDesc = attachDesc;
    }
    public String getAttachSha1() {
        return attachSha1;
    }
    public void setAttachSha1(String attachSha1) {
        this.attachSha1 = attachSha1;
    }
    public int getImageWidth() {
        return imageWidth;
    }
    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }
    public int getImageHeight() {
        return imageHeight;
    }
    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }
    public String getCruser() {
        return cruser;
    }
    public void setCruser(String cruser) {
        this.cruser = cruser;
    }
    public Timestamp getCrtime() {
        return crtime;
    }
    public void setCrtime(Timestamp crtime) {
        this.crtime = crtime;
    }
    public String getMduser() {
        return mduser;
    }
    public void setMduser(String mduser) {
        this.mduser = mduser;
    }
    public Timestamp getMdtime() {
        return mdtime;
    }
    public void setMdtime(Timestamp mdtime) {
        this.mdtime = mdtime;
    }
    public String getRefId() {
        return refId;
    }
    public void setRefId(String refId) {
        this.refId = refId;
    }

    public MultipartFile[] getFiles() {
        return files;
    }
    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
    public String getModule() {
        return module;
    }
    public void setModule(String module) {
        this.module = module;
    }
    public String getEntity() {
        return entity;
    }
    public void setEntity(String entity) {
        this.entity = entity;
    }
    public String getAttr() {
        return attr;
    }
    public void setAttr(String attr) {
        this.attr = attr;
    }
    
    public List<String> getRefIds() {
        return refIds;
    }
    public void setRefIds(List<String> refIds) {
        this.refIds = refIds;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    
}
