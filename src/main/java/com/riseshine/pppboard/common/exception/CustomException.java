package com.riseshine.pppboard.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException{
  private String msg;
  private HttpStatus status;

  public CustomException(String msg, HttpStatus httpStatus) {
    super(msg);
    this.msg = msg;
    this.status = httpStatus;
  }
}
