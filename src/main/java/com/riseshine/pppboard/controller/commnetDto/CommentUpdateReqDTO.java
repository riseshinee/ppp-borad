package com.riseshine.pppboard.controller.commnetDto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class CommentUpdateReqDTO {
  @NotBlank
  @NotEmpty(message = "댓글 내용은 필수 입력값입니다.")
  @Size(min = 1)
  @Parameter(description = "내용")
  String content;
}
