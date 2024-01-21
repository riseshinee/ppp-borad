package com.riseshine.pppboard.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class ErrorMessage {
    private int errorCode;
    private String errorMessage;
    private int status;
}
