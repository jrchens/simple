package me.simple.commons.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;

public class Channel implements Serializable {
    // ===
    private List<Channel> children = Lists.newArrayList();
    // ===
    @Length(groups={Save.class,Get.class,Remove.class},min=1,max=32)
    private String channelName;
    @Length(groups={Save.class,Update.class},min=0,max=32)
    private String parentName;
    @Length(groups={Save.class,Update.class},min=1,max=32)
    private String viewname;
    @Length(groups={Save.class,Update.class},min=0,max=128)
    private String url;
    
    private String owner;
    private String cruser; // varchar(32) DEFAULT NULL COMMENT '创建者',
    private Timestamp crtime; // datetime DEFAULT NULL COMMENT '创建时间',
    private String mduser; // varchar(32) DEFAULT NULL COMMENT '修改者',
    private Timestamp mdtime; // datetime DEFAULT NULL COMMENT '修改用户',
    public String getChannelName() {
        return channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public String getViewname() {
        return viewname;
    }
    public void setViewname(String viewname) {
        this.viewname = viewname;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
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
    public List<Channel> getChildren() {
        return children;
    }
    public void setChildren(List<Channel> children) {
        this.children = children;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
