package com.riseshine.pppboard.controller.commnetDto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class CommentCreateReqDTO {

  @Parameter(description = "부모 댓글 no")
  int parentNo;

  @NotNull(message = "게시글 PK는 필수 입력 값입니다.")
  @Parameter(description = "게시글 no")
  int postNo;

  @NotNull(message = "유저 PK는 필수 입력 값입니다.")
  @Parameter(description = "유저 no")
  int userNo;

  @NotBlank
  @NotEmpty(message = "댓글 내용은 필수 입력값입니다.")
  @Size(min = 1)
  @Parameter(description = "내용")
  String content;
}
