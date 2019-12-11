package com.unicomlabs.das.virtualization.utils;

public class BaseInVo {
	/** 每页的记录数*/
	private int pageSize;
	
	/** 当前页数 */
	private int currentPage;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
