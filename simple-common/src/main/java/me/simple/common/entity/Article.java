package me.simple.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class Article implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3543063964005985255L;
    // ===
    private int channelId;
    private String channelNameValue;
    // ===

    @NotBlank(groups={Get.class,Remove.class})
//    @Length(groups={Get.class,Remove.class},min=32,max=32)
    private String articleId; // varchar(32) NOT NULL COMMENT '文章主键',

    @Length(groups={Save.class,Update.class},min=1,max=32)
    private String channelName; // varchar(32) DEFAULT NULL COMMENT '频道名称',
    @Length(groups={Save.class,Update.class},min=1,max=128)
    private String title; // varchar(128) DEFAULT NULL COMMENT '标题',
    @Length(groups={Save.class,Update.class},min=0,max=255)
    private String intro; // varchar(255) DEFAULT NULL COMMENT '简介',
    @Length(groups={Save.class,Update.class},min=0,max=128)
    private String origin; // varchar(128) DEFAULT NULL COMMENT '来源',
    @Length(groups={Save.class,Update.class},min=1,max=65535)
    private String richContent; // text COMMENT '内容',
    @Length(groups={Save.class,Update.class},min=0,max=32)
    private String author; // varchar(32) DEFAULT NULL COMMENT '作者',
    
    // @Pattern(groups={Save.class,Update.class},regexp="yyyy-MM-dd")
    // @NotEmpty(groups={Save.class,Update.class})
    @DateTimeFormat(iso=ISO.DATE)
    private java.sql.Date pubDate; // date DEFAULT NULL COMMENT '发布日期',
    
    private int viewCount; // int(11) DEFAULT '0' COMMENT '查看次数',
    private boolean disabled; // tinyint(1) DEFAULT '0' COMMENT '禁用',
    private String owner; // varchar(32) DEFAULT NULL COMMENT '所有者',
    private String cruser; // varchar(32) DEFAULT NULL COMMENT '创建者',
    private Timestamp crtime; // datetime DEFAULT NULL COMMENT '创建时间',
    private String mduser; // varchar(32) DEFAULT NULL COMMENT '修改者',
    private Timestamp mdtime; // datetime DEFAULT NULL COMMENT '修改用户',

    public String getArticleId() {
	return articleId;
    }

    public void setArticleId(String articleId) {
	this.articleId = articleId;
    }

    public String getChannelName() {
	return channelName;
    }

    public void setChannelName(String channelName) {
	this.channelName = channelName;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getIntro() {
	return intro;
    }

    public void setIntro(String intro) {
	this.intro = intro;
    }

    public String getOrigin() {
	return origin;
    }

    public void setOrigin(String origin) {
	this.origin = origin;
    }

    public String getRichContent() {
	return richContent;
    }

    public void setRichContent(String richContent) {
	this.richContent = richContent;
    }

    public String getAuthor() {
	return author;
    }

    public void setAuthor(String author) {
	this.author = author;
    }

    public java.sql.Date getPubDate() {
	return pubDate;
    }

    public void setPubDate(java.sql.Date pubDate) {
	this.pubDate = pubDate;
    }

    public int getViewCount() {
	return viewCount;
    }

    public void setViewCount(int viewCount) {
	this.viewCount = viewCount;
    }

    public boolean isDisabled() {
	return disabled;
    }

    public void setDisabled(boolean disabled) {
	this.disabled = disabled;
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

    public String getOwner() {
	return owner;
    }

    public void setOwner(String owner) {
	this.owner = owner;
    }

    public String getChannelNameValue() {
	return channelNameValue;
    }

    public void setChannelNameValue(String channelNameValue) {
	this.channelNameValue = channelNameValue;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

}
