package com.flockinger.spongeblogui.dto;

import com.flockinger.spongeblogui.controller.helper.Linkable;

public class UserInfo implements Linkable {
  private Long id;
  private String name;
  private String link;

  public UserInfo() {}

  public UserInfo(Long id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
