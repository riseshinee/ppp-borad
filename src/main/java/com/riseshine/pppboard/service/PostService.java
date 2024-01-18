package com.riseshine.pppboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.riseshine.pppboard.domain.Post;
import com.riseshine.pppboard.dao.PostRepository;

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
   * @param title
   * @param content
   * @return
   */
  public Integer savePost(Integer userNo, String userName, String title, String content) {
    Post post = createPendingPost(userNo, userName, title, content);
    return postRepository.save(post).getNo();
  }

  /**
   * post 객체 생성
   * @param userNo
   * @param userName
   * @param title
   * @param content
   * @return
   */
  private Post createPendingPost(Integer userNo, String userName, String title, String content) {
    return Post.builder()
            .userNo(userNo)
            .userName(userName)
            .title(title)
            .content(content)
            .build();
  }
}
