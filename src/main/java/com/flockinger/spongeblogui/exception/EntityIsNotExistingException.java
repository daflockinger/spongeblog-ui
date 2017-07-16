package com.flockinger.spongeblogui.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntityIsNotExistingException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -6737236529802687968L;

  public EntityIsNotExistingException(String message) {
    super(message);
  }
}
