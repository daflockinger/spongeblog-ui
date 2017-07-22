package com.flockinger.spongeblogui.dto;

import com.flockinger.spongeblogui.resource.dto.PostStatus;

public interface PostQueryBuilder {
  PostQuery build();

  PostQueryBuilder status(PostStatus status);

  PostQueryBuilder size(Integer size);

  PostQueryBuilder page(Integer page);

  PostQueryBuilder path(String path);
}
