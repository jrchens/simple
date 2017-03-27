package me.simple.common.entity;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.hibernate.validator.constraints.Length;

public class Tag extends Base {

    /**
     * 
     */
    private static final long serialVersionUID = 243881296649726476L;
    @Length(groups={Save.class,Update.class,Get.class,Remove.class},min=1,max=32)
    private String tagname;
    @Length(groups={Save.class,Update.class},min=1,max=32)
    private String viewname;

    private String owner;
    
    public String getTagname() {
        return tagname;
    }
    public void setTagname(String tagname) {
        this.tagname = tagname;
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
    
}
