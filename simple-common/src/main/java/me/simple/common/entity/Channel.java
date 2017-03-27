package me.simple.common.entity;

import java.util.List;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;

public class Channel extends Base {
    /**
     * 
     */
    private static final long serialVersionUID = 239514299929506595L;
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
