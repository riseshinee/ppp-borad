package com.riseshine.pppboard.common;

import java.util.Arrays;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCode {

  // 공통
  SUCCESS(HttpStatus.OK, "PPP_00000", "success"),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "PPP_99999", "server error"),
  USER_NOT_EXIST(HttpStatus.NOT_FOUND, "PPP_10404", "회원 정보가 존재하지 않습니다."),
  ID_DUPLICATION(HttpStatus.CONFLICT, "PPP_10409", "중복된 아이디가 존재합니다.");


  private final HttpStatus status;
  private final String code;
  private final String message;

  ResultCode(final HttpStatus status, final String code, final String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public static ResultCode findByCode(final String code) {
    return Arrays.stream(values()).filter(value -> value.getCode().equals(code)).findFirst().orElse(null);
  }
}
