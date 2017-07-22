package com.flockinger.spongeblogui.dto;

public class Pagination {
  private String nextLink;
  private String previousLink;

  public String getNextLink() {
    return nextLink;
  }

  public void setNextLink(String nextLink) {
    this.nextLink = nextLink;
  }

  public String getPreviousLink() {
    return previousLink;
  }

  public void setPreviousLink(String previousLink) {
    this.previousLink = previousLink;
  }
}
