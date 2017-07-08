package com.flockinger.spongeblogui.dto;

public class Tag {
	private Long id;
	private String name;
	private String link;
	
	public Tag(String name, Long id) {
		super();
		this.name = name;
		this.id = id;
	}
	
	public Tag() {}
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
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
