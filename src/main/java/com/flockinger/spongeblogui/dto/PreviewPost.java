package com.flockinger.spongeblogui.dto;

import java.util.Date;
import java.util.List;

import com.flockinger.spongeblogui.controller.helper.Postable;

public class PreviewPost implements Postable {

  private Long postId;
  private String link;
  private String title;
  private Date created;
  private Date modified;
  private UserInfo user;
  private List<Tag> tags;

  private String partContent;

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public UserInfo getUser() {
    return user;
  }

  public void setUser(UserInfo user) {
    this.user = user;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getModified() {
    return modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public String getPartContent() {
    return partContent;
  }

  public void setPartContent(String partContent) {
    this.partContent = partContent;
  }
}
