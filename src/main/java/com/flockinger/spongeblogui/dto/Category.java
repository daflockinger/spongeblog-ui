package com.flockinger.spongeblogui.dto;

import java.util.List;

public class Category {
	private Long id;
	private String name;
	private String link;
	private List<Category> children;
	
	public Category(String name, Long id, List<Category> children) {
		this.name = name;
		this.children = children;
		this.id = id;
	}
	
	public Category() {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Category> getChildren() {
		return children;
	}
	public void setChildren(List<Category> children) {
		this.children = children;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
