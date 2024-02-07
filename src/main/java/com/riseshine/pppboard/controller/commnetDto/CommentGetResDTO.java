package com.riseshine.pppboard.controller.commnetDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Builder
public class CommentGetResDTO {
  String userName;
  String content;
}