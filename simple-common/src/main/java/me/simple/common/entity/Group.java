package me.simple.common.entity;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.hibernate.validator.constraints.Length;

public class Group extends Base {

    /**
     * 
     */
    private static final long serialVersionUID = 5845791634698346684L;
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
