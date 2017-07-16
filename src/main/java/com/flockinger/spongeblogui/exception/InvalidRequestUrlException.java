package com.flockinger.spongeblogui.exception;

public class InvalidRequestUrlException extends RuntimeException {
  /**
   * 
   */
  private static final long serialVersionUID = 4870399070820387990L;

  public InvalidRequestUrlException(String message) {
    super(message);
  }
}
