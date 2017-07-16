package com.flockinger.spongeblogui.resource.dto;

import org.springframework.hateoas.ResourceSupport;

public class PostLink extends ResourceSupport {
  private Long postId;

  public Long getPostId() {
    return postId;
  }

  public void setId(Long postId) {
    this.postId = postId;
  }
}
