package com.riseshine.pppboard.common.exception;

import com.riseshine.pppboard.common.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RestResponse {
  private String code;
  private String message;
  private HttpStatus status;
  private List<CustomFieldError> errors;

  public static class CustomFieldError{
    private String field;
    private String value;
    private String reason;

    private CustomFieldError(String field, String value, String reason) {
      this.field = field;
      this.value = value;
      this.reason = reason;
    }

    private CustomFieldError(FieldError fieldError){
      this.field = fieldError.getField();
      this.value = fieldError.getRejectedValue().toString();
      this.reason = fieldError.getDefaultMessage();
    }

    public String getField() { return field; }
    public String getValue() { return value; }
    public String getReason() { return reason; }
  }

  private void setErrorCode(ResultCode errorCode){
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
    this.status = errorCode.getStatus();
  }

  private RestResponse(ResultCode errorCode, List<FieldError> errors) {
    setErrorCode(errorCode);
    this.errors = errors.stream().map(CustomFieldError::new).collect(Collectors.toList());
  }

  private RestResponse(ResultCode errorCode, String exceptionMessage) {
    setErrorCode(errorCode);
    this.errors = List.of(new CustomFieldError("", "", exceptionMessage));
  }

  public static RestResponse of(ResultCode errorCode){
    return new RestResponse(errorCode, Collections.emptyList());
  }

  public static RestResponse of(ResultCode errorCode, BindingResult bindingResult){
    return new RestResponse(errorCode, bindingResult.getFieldErrors());
  }

  public static RestResponse of(ResultCode errorCode, String exceptionMessage){
    return new RestResponse(errorCode, exceptionMessage);
  }

}
