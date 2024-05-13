package com.riseshine.pppboard.common.exception;

import com.riseshine.pppboard.common.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException{
  private HttpStatus status;
  private String code;
  private String message;

  public CustomException(ResultCode resultCode) {
    super();
    this.status = resultCode.getStatus();
    this.code = resultCode.getCode();
    this.message = resultCode.getMessage();
  }
}
