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
  ID_DUPLICATION(HttpStatus.CONFLICT, "PPP_10409", "중복된 아이디가 존재합니다."),
  ID_NOT_EXIST(HttpStatus.NOT_FOUND, "PPP_11404", "아이디가 존재하지 않습니다."),
  PASSWORD_INVALID(HttpStatus.UNAUTHORIZED, "PPP_10401", "비밀번호가 일치하지 않습니다."),
  POST_NOT_EXIST(HttpStatus.NOT_FOUND, "PPP_20404", "게시글이 존재하지 않습니다."),
  FILE_ATTACH_COUNT(HttpStatus.PAYLOAD_TOO_LARGE, "PPP_30413", "첨부 파일의 갯수는 최대 5개 입니ㅑ."),
  FILE_TYPE_INVALID(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "PPP_30415", "지원하지 않는 이미지 형식입니다."),
  FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "PPP_30500", "파일 업로드 실패"),
  FILE_NOT_EXIST(HttpStatus.NOT_FOUND, "PPP_30404", "파일이 존재하지 않습니다."),
  FILE_SEQ_DUPLICATED(HttpStatus.CONFLICT, "PPP_30409", "중복된 파일 순서가 발견되었습니다.");


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
