package com.riseshine.pppboard.controller.commnetDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class CommentGetResDTO {
  private int no;
  private String userName;
  private String content;
  private List<CommentGetResDTO> childComments;
  public CommentGetResDTO(int no, String userName, String content, List<CommentGetResDTO> childComments) {
    this.no = no;
    this.userName = userName;
    this.content = content;
    this.childComments = childComments;
  }
}