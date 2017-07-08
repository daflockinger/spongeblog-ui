package com.flockinger.spongeblogui.dto;

import java.util.Optional;

public class Paging {
	private int page = 0;
	private int size;
	private String path;
	
	public Paging(Optional<Integer> page, int size, String path) {
		this.path = path;
		this.size = size;
		
		if(page.isPresent()) {
			this.page = page.get();
		}
	}
	
	public String getPath() {
		return path;
	}
	public int getPage() {
		return page;
	}
	public int getSize() {
		return size;
	}
}
