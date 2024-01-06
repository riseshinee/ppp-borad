package com.riseshine.pppboard.common.exception;

public enum ResultCode {
  // 공통
  INVALID_INPUT_VALUE(400, "COM_001", "Invalid Input Value"),
  METHOD_NOT_ALLOWED(405, "COM_002", "Method not allowed"),
  HANDLE_ACCESS_DENIED(403, "COM_003", "Access is Denied"),

  // Standard
  ILLEGAL_STATE(400, "STD_001", "illegal state"),
  ILLEGAL_ARGUMENT(400, "STD_002", "illegal argument"),

  // Exception
  EXCEPTION(500, "EXCEPTION", "exception");

  private final String code;
  private final String message;
  private int status;

  ResultCode(final int status, final String code, final String message) {
    this.status = status;
    this.message = message;
    this.code = code;
  }

  protected String getCode() { return code; }
  protected String getMessage() { return message; }
  protected int getStatus() { return status; }
}