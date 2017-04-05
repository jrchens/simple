package me.simple.entity;

import java.util.List;

import com.google.common.collect.Lists;

public class Table implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7941997291287286299L;
    private String tab_name;
    private List<Column> columns = Lists.newArrayList();
    
    public Table() {
	super();
    }
    public Table(String tab_name) {
	super();
	this.setTab_name(tab_name);
    }
    public List<Column> getColumns() {
	return columns;
    }
    public void setColumns(List<Column> columns) {
	this.columns = columns;
    }
    public String getTab_name() {
	return tab_name;
    }
    public void setTab_name(String tab_name) {
	this.tab_name = tab_name;
    }
    
    
}
