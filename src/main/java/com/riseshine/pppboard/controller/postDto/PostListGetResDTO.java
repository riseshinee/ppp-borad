package com.riseshine.pppboard.controller.postDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

import com.riseshine.pppboard.domain.Post;

@Getter
@NoArgsConstructor
public class PostListGetResDTO {

  private List<PostListDTO> postDtoList;
  private long totalPage;
  private long totalCount;

  @Builder
  public PostListGetResDTO(List<Post> postList, long totalPage, long totalCount) {
    this.postDtoList = postList.stream()
            .map(PostListDTO::new).collect(Collectors.toList());
    this.totalPage = totalPage;
    this.totalCount = totalCount;
  }
}
