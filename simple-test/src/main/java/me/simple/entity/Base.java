package me.simple.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Base implements java.io.Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 4698283281033542672L;
    public Base() {
	super();
    }
    public Base(Long rowId) {
	super();
	this.rowId = rowId;
    }

    private Long rowId; // AUTO_INCREMENT
    private String name;
    private Double price;
    private Date pubDate;

    private Boolean deleted;
    private Boolean disabled;
    private Integer rowStatus;
    private String cruser;
    private Timestamp crtime;
    private String mduser;
    private Timestamp mdtime;
    
    private transient String recordId;
    private transient Integer page; // offset
    private transient Integer rows; // row_count
    private transient Integer total;
    private transient String srt;
    private transient String ord;
    public Long getRowId() {
        return rowId;
    }
    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Date getPubDate() {
        return pubDate;
    }
    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }
    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public Boolean getDisabled() {
        return disabled;
    }
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
    public Integer getRowStatus() {
        return rowStatus;
    }
    public void setRowStatus(Integer rowStatus) {
        this.rowStatus = rowStatus;
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
    public String getRecordId() {
        return recordId;
    }
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getRows() {
        return rows;
    }
    public void setRows(Integer rows) {
        this.rows = rows;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public String getSrt() {
        return srt;
    }
    public void setSrt(String srt) {
        this.srt = srt;
    }
    public String getOrd() {
        return ord;
    }
    public void setOrd(String ord) {
        this.ord = ord;
    }
    
    
    
    
    
}
