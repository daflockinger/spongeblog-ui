package com.flockinger.spongeblogui.dto;

public class AjaxPagination {
	private int pageNumber;
	private boolean hasNext;
	private boolean hasPrevious;
	
	public int getPageNumber() {
		return pageNumber;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public boolean isHasPrevious() {
		return hasPrevious;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}
}
