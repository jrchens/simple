package me.simple.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Base implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4565854181961319190L;
    private boolean deleted;
    private String cruser;
    private Timestamp crtime;
    private String mduser;
    private Timestamp mdtime;

    public boolean isDeleted() {
	return deleted;
    }

    public void setDeleted(boolean deleted) {
	this.deleted = deleted;
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
