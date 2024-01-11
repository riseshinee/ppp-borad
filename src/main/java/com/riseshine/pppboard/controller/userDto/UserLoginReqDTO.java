package com.riseshine.pppboard.controller.userDto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class UserLoginReqDTO {
  @NotBlank
  @NotEmpty(message = "아이디는 필수 입력 값입니다.")
  @Size(min = 6, max = 12, message = "아이디는 최소 6자 최대 12자 입니다.")
  @Parameter(description = "유저 Id")
  String id;

  @NotBlank
  @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
  @Size(min = 8, max = 12, message = "비밀번호는 최소 8자 최대 12자 입니다.")
  @Parameter(description = "비밀번호")
  String password;
}
