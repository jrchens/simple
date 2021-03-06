package me.simple.common.entity;

public class AttachmentRef extends Base {
    /**
     * 
     */
    private static final long serialVersionUID = -2829369986086706405L;
    private String refId;
    private String attachId;
    private String module;
    private String entity;
    private String attr;
    private String owner;
    private int srt;

    public String getRefId() {
	return refId;
    }

    public void setRefId(String refId) {
	this.refId = refId;
    }

    public String getAttachId() {
	return attachId;
    }

    public void setAttachId(String attachId) {
	this.attachId = attachId;
    }

    public String getModule() {
	return module;
    }

    public void setModule(String module) {
	this.module = module;
    }

    public String getEntity() {
	return entity;
    }

    public void setEntity(String entity) {
	this.entity = entity;
    }

    public String getAttr() {
	return attr;
    }

    public void setAttr(String attr) {
	this.attr = attr;
    }

    public String getOwner() {
	return owner;
    }

    public void setOwner(String owner) {
	this.owner = owner;
    }

    public int getSrt() {
	return srt;
    }

    public void setSrt(int srt) {
	this.srt = srt;
    }

}
