package com.flockinger.spongeblogui.resource.dto;


/**
 * Status of the Blog.
 */
public enum BlogStatus {
	
	ACTIVE("ACTIVE"),

	DISABLED("DISABLED"),

	MAINTENANCE("MAINTENANCE");

	private String value;

	BlogStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
