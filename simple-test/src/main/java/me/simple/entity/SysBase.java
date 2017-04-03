package me.simple.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class SysBase extends Base {
    
    /**
     * TODO serialVersionUID
     */
    
    public SysBase() {
      super();
    }
    public SysBase(Long row_id) {
      super();
      this.row_id = row_id;
    }

    private Long row_id;
    private String name;
    private Double price;
    private Date pub_date;
    private Integer row_status;
    private Boolean disabled;
    private Boolean deleted;
    private String cruser;
    private Timestamp crtime;
    private String mduser;
    private Timestamp mdtime;

    public Long getRow_id() {
      return row_id;
    }
    public void setRow_id(Long row_id) {
      this.row_id = row_id;
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
    public Date getPub_date() {
      return pub_date;
    }
    public void setPub_date(Date pub_date) {
      this.pub_date = pub_date;
    }
    public Integer getRow_status() {
      return row_status;
    }
    public void setRow_status(Integer row_status) {
      this.row_status = row_status;
    }
    public Boolean getDisabled() {
      return disabled;
    }
    public void setDisabled(Boolean disabled) {
      this.disabled = disabled;
    }
    public Boolean getDeleted() {
      return deleted;
    }
    public void setDeleted(Boolean deleted) {
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
