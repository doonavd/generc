package com.ccq.framework.lang;

import java.io.Serializable;

public class Page implements Serializable{

	private static final long serialVersionUID = 9000151198919642793L;

	private int pages;
	private int pageNum;
	private int pageSize;
	
	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Page(int pages, int pageNum, int pageSize) {
		super();
		this.pages = pages;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
