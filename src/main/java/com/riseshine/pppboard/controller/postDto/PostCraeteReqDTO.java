package com.riseshine.pppboard.controller.postDto;

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
public class PostCraeteReqDTO {
  @NotBlank
  @NotEmpty(message = "제목은 필수 입력 값입니다.")
  @Size(min = 5, max = 30, message = "제목은 최소 5자 최대 30자 입니다.")
  @Parameter(description = "제목")
  String title;

  @NotBlank
  @NotEmpty(message = "내용은 필수 입력 값입니다.")
  @Size(min = 10, message = "내용은 최소 10자입니다.")
  @Parameter(description = "내용")
  String content;

}
