package com.riseshine.pppboard.controller.postDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import com.riseshine.pppboard.domain.Post;

@Getter
@NoArgsConstructor
public class PostListDTO {
  private int no;
  private String userName;
  private String title;
  private LocalDateTime createdAt;

  public PostListDTO(Post post) {
    this.no = post.getNo();
    this.userName = post.getUserName();
    this.title = post.getTitle();
    this.createdAt = post.getCreatedAt();
  }

}
