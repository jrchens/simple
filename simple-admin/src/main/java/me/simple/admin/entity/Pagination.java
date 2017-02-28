package me.simple.admin.entity;

import java.util.List;

import com.google.common.collect.Lists;

public class Pagination<T> {
    private int currentPage = 1;
    private int pageSize = 10;
    private int visiblePage = 10;
    private int totalPage = 1;
    private int totalRecord = 0;
    private List<T> records;

    private String order;
    private String sort;
    
    private int offset = 1;

    public int getCurrentPage() {
	return currentPage;
    }

    public void setCurrentPage(int currentPage) {
	this.currentPage = currentPage;
    }

    public int getPageSize() {
	return pageSize;
    }

    public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
    }

    public int getVisiblePage() {
	return visiblePage;
    }

    public void setVisiblePage(int visiblePage) {
	this.visiblePage = visiblePage;
    }

    public int getTotalPage() {
	if(totalRecord < pageSize){
	    return 1;
	}
	totalPage = totalRecord / pageSize;
	if (totalRecord % pageSize > 0) {
	    totalPage++;
	}
	return totalPage;
    }

    public void setTotalPage(int totalPage) {
	this.totalPage = totalPage;
    }

    public int getTotalRecord() {
	return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
	this.totalRecord = totalRecord;
    }

    public List<T> getRecords() {
	if (records == null)
	    records = Lists.newArrayList();
	return records;
    }

    public void setRecords(List<T> records) {
	this.records = records;
    }

    public String getOrder() {
	return order;
    }

    public void setOrder(String order) {
	this.order = order;
    }

    public String getSort() {
	return sort;
    }

    public void setSort(String sort) {
	this.sort = sort;
    }

    public int getOffset() {
	offset = (currentPage - 1) * pageSize;
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
