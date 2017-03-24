package me.simple.commons.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Product implements Serializable {

    // ===
    @NotEmpty(groups={Save.class,Update.class})
    private List<String> tags;
    // ===

    @Length(groups={Get.class,Remove.class},min=1,max=32)
    private String productId;

    @Length(groups={Save.class,Update.class},min=1,max=32)
    private String productName;
    @Length(groups={Save.class,Update.class},min=0,max=128)
    private String intro;
    @Length(groups={Save.class,Update.class},min=0,max=64)
    private String spec;
    @Length(groups={Save.class,Update.class},min=0,max=65535)
    private String richContent;
    

    private String owner;
    private String cruser;
    private Timestamp crtime;
    private String mduser;
    private Timestamp mdtime;
    
    
    
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getIntro() {
        return intro;
    }
    public void setIntro(String intro) {
        this.intro = intro;
    }
    public String getSpec() {
        return spec;
    }
    public void setSpec(String spec) {
        this.spec = spec;
    }
    public String getRichContent() {
        return richContent;
    }
    public void setRichContent(String richContent) {
        this.richContent = richContent;
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
