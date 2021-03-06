package com.flockinger.spongeblogui.resource.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * UserEditDTO
 */

public class UserEditDTO extends ResourceSupport {

  private Long userId = null;

  @NotEmpty
  private String login = null;

  @Length(min = 6)
  private String password = null;

  @NotEmpty
  private String nickName = null;

  @NotEmpty
  @Email
  private String email = null;

  @NotNull
  private Date registered = null;

  private List<UserRole> roles = new ArrayList<UserRole>();

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
   * Login name of the User.
   * 
   * @return login
   **/
  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  /**
   * Password hash of the User.
   * 
   * @return password
   **/
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  /**
   * Roles of the User (authorizations).
   * 
   * @return roles
   **/
  public List<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(List<UserRole> roles) {
    this.roles = roles;
  }
}
