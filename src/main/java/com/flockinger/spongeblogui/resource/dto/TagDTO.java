package com.flockinger.spongeblogui.resource.dto;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TagDTO otherTag = (TagDTO) o;
    return Objects.equals(this.name, otherTag.name) && Objects.equals(this.tagId, otherTag.tagId);
  }
}
