package com.riseshine.pppboard;

import com.riseshine.pppboard.common.ResultCode;
import com.riseshine.pppboard.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.riseshine.pppboard.common.exception.*;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<String> customException(CustomException customException){
    return new ResponseEntity<>(customException.getMessage(), customException.getStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<RestResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    final RestResponse response = RestResponse.of(ResultCode.INTERNAL_SERVER_ERROR, e.getBindingResult());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
