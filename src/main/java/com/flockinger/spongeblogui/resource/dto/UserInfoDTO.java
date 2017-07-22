package com.flockinger.spongeblogui.resource.dto;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

/**
 * UserInfoDTO
 */
public class UserInfoDTO extends ResourceSupport {

  private Long userId = null;
  private String nickName = null;
  private String email = null;
  private Date registered = null;

  /**
   * Unique identifier.
   * 
   * @return userId
   **/
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Display nickname of the User.
   * 
   * @return nickName
   **/
  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  /**
   * Email of User.
   * 
   * @return email
   **/
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Registration date of User in long.
   * 
   * @return registered
   **/
  public Date getRegistered() {
    return registered;
  }

  public void setRegistered(Date registered) {
    this.registered = registered;
  }
}
