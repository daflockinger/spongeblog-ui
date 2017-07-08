package com.flockinger.spongeblogui.resource.dto;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

/**
 * TagDTO
 */

public class TagDTO extends ResourceSupport {

	private Long tagId = null;

	@NotEmpty
	private String name = null;

	/**
	 * Unique identifier.
	 * 
	 * @return tagId
	 **/
	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	/**
	 * Tag name.
	 * 
	 * @return name
	 **/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
