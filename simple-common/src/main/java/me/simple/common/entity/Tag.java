package me.simple.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.hibernate.validator.constraints.Length;

public class Tag implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7236696718732948662L;
    @Length(groups={Save.class,Update.class,Get.class,Remove.class},min=1,max=32)
    private String tagname;
    @Length(groups={Save.class,Update.class},min=1,max=32)
    private String viewname;

    private String owner;
    private String cruser;
    private Timestamp crtime;
    private String mduser;
    private Timestamp mdtime;
    
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
    
    
    
}
