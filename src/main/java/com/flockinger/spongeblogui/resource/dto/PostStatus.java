package com.flockinger.spongeblogui.resource.dto;

/**
 * Display status of the Post.
 */
public enum PostStatus {
	ALL("ALL"),
	
	PUBLIC("PUBLIC"),

	PRIVATE("PRIVATE"),

	MAINTENANCE("MAINTENANCE"),

	DELETED("DELETED");

	private String value;

	PostStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
