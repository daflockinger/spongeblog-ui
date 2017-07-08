package com.flockinger.spongeblogui.resource.dto;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

/**
 * CategoryDTO
 */
public class CategoryDTO extends ResourceSupport {

	private Long categoryId = null;

	@NotEmpty
	private String name = null;

	private Long parentId = null;

	private Integer rank = null;


	/**
	 * Unique identifier.
	 * 
	 * @return categoryId
	 **/
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * Category display name.
	 * 
	 * @return name
	 **/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Id of parent Category.
	 * 
	 * @return parentId
	 **/
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * Determines position of Category.
	 * 
	 * @return rank
	 **/
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
}
