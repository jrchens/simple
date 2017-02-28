package me.simple.admin.entity;

import java.io.Serializable;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.hibernate.validator.constraints.Length;

public class Group implements Serializable {

    @Length(groups={Save.class,Update.class,Get.class,Remove.class},min=1,max=32)
    private String groupname;
    @Length(groups={Save.class,Update.class},min=1,max=32)
    private String viewname;
    
    public String getGroupname() {
        return groupname;
    }
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
    public String getViewname() {
        return viewname;
    }
    public void setViewname(String viewname) {
        this.viewname = viewname;
    }
    
    
    
}
