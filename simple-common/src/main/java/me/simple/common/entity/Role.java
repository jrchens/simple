package me.simple.common.entity;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.hibernate.validator.constraints.Length;

public class Role extends Base {

    /**
     * 
     */
    private static final long serialVersionUID = 1532301532700112111L;
    @Length(groups={Save.class,Update.class,Get.class,Remove.class},min=1,max=32)
    private String rolename;
    @Length(groups={Save.class,Update.class},min=1,max=32)
    private String viewname;
    public String getRolename() {
        return rolename;
    }
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
    public String getViewname() {
        return viewname;
    }
    public void setViewname(String viewname) {
        this.viewname = viewname;
    }
    
    
    
}
