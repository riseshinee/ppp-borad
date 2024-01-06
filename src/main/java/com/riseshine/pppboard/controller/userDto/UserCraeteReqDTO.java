package com.riseshine.pppboard.controller.userDto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class UserCraeteReqDTO {
    @Parameter(description = "유저 Id")
    String id;

    @Parameter(description = "유저 이름")
    String name;

    @Parameter(description = "비밀번호")
    String password;
}
