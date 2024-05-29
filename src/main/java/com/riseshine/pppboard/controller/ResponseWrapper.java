package com.riseshine.pppboard.controller;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.riseshine.pppboard.common.exception.ErrorMessage;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseWrapper<T> extends ErrorMessage {

    private T data;
    private int status;
    private String code;

    public ResponseWrapper() {
        this.status = HttpStatus.OK.value();
    }

    public ResponseWrapper(T data) {
        this.status = HttpStatus.OK.value();
        this.data = data;
    }
}
