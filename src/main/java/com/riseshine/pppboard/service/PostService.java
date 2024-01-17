package com.riseshine.pppboard.service;

import com.riseshine.pppboard.common.utils.CommonUtil;
import com.riseshine.pppboard.controller.userDto.UserCraeteReqDTO;
import com.riseshine.pppboard.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.riseshine.pppboard.domain.Post;
import com.riseshine.pppboard.dao.PostRepository;
import com.riseshine.pppboard.controller.postDto.PostCraeteReqDTO;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  /**
   * 게시글 생성
   * @param userNo
   * @param userName
   * @param createPostDto
   * @return
   */
  public Integer savePost(Integer userNo, String userName, PostCraeteReqDTO createPostDto) {
    Post post = createPendingPost(userNo, userName, createPostDto);
    return postRepository.save(post).getNo();
  }

  /**
   * post 객체 생성
   * @param createPostDto
   * @return
   */
  private Post createPendingPost(Integer userNo, String userName, PostCraeteReqDTO createPostDto) {
    return Post.builder()
            .userNo(userNo)
            .userName(userName)
            .title(createPostDto.getTitle())
            .content(createPostDto.getContent())
            .build();
  }
}
