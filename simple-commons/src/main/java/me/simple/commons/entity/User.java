package me.simple.commons.entity;

import java.io.Serializable;
import java.util.List;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class User implements Serializable {
    
    // ===
    @NotEmpty(groups={Update.class})
    private List<String> roles;
    // ===

    @Length(groups={Save.class,Update.class,Get.class,Remove.class},min=1,max=32)
    private String username;
    @Length(groups={Save.class,Update.class},min=1,max=32)
    private String viewname;
    @Length(groups={Save.class},min=6,max=128)
    private String password;
    @Length(groups={Save.class,Update.class},min=1,max=32)
    private String groupname;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getViewname() {
        return viewname;
    }
    public void setViewname(String viewname) {
        this.viewname = viewname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getGroupname() {
        return groupname;
    }
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    
}
