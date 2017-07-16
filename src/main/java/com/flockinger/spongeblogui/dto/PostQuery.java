package com.flockinger.spongeblogui.dto;

import java.util.Optional;

import com.flockinger.spongeblogui.resource.dto.PostStatus;

public class PostQuery implements PostQueryBuilder, PostQueryIdBuilder {
  public final static Optional<Integer> DEFAULT_PAGE = Optional.of(0);
  public final static Integer DEFAULT_SIZE = 5;

  private PostFilter filter;
  private PostStatus status = PostStatus.ALL;
  private Long id;
  private Paging paging = new Paging(DEFAULT_PAGE, DEFAULT_SIZE, "");

  private PostQuery(PostFilter filter) {
    this.filter = filter;
  }

  public static PostQueryIdBuilder filter(PostFilter filter) {
    return new PostQuery(filter);
  }

  public static PostQueryBuilder all() {
    return new PostQuery(PostFilter.ALL);
  }

  public PostStatus getStatus() {
    return status;
  }

  public PostQueryBuilder status(PostStatus status) {
    this.status = status;
    return this;
  }

  public Long getId() {
    return id;
  }

  public Paging getPaging() {
    return paging;
  }

  public PostQueryBuilder size(Integer size) {
    this.paging.setSize(size);
    return this;
  }

  public PostQueryBuilder page(Integer page) {
    this.paging.setPage(page);
    return this;
  }

  public PostQueryBuilder path(String path) {
    this.paging.setPath(path);
    return this;
  }

  public PostFilter getFilter() {
    return filter;
  }

  public PostQueryBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public PostQuery build() {
    return this;
  }
}
