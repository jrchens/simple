package me.simple.common.entity;

import java.util.List;

import javax.validation.groups.Get;
import javax.validation.groups.Remove;
import javax.validation.groups.Save;
import javax.validation.groups.Update;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Product extends Base {

    /**
     * 
     */
    private static final long serialVersionUID = -5270608714148365292L;

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
    
    
}
