package com.flockinger.spongeblogui.controller.helper;

public interface Navigable {
  public Boolean getHasNext();

  public Boolean getHasPrevious();

  public Integer getTotalPages();
}
