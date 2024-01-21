package com.riseshine.pppboard.service;

import com.riseshine.pppboard.controller.fileInfoDto.FileInfoGetResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.riseshine.pppboard.common.exception.CustomException;
import com.riseshine.pppboard.domain.Post;
import com.riseshine.pppboard.dao.PostRepository;
import com.riseshine.pppboard.controller.postDto.*;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final FileService fileService;

  /**
   * 게시글 생성
   * @param userNo
   * @param userName
   * @param title
   * @param content
   * @return
   */
  public int savePost(int userNo, String userName, String title, String content) {
    Post post = createPendingPost(userNo, userName, title, content);
    return postRepository.save(post).getNo();
  }

  /**
   * 게시글 조회
   * @param no
   * @return
   * @throws Exception
   */
  public PostGetResDTO getPost(int no) throws Exception {
    Post post = postRepository.findFirstByNo(no).orElseThrow(() ->
            new CustomException("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST)
    );
    //첨부 이미지 리스트 return
    List<FileInfoGetResDTO> fileInfos = fileService.getFilesByPostNo(no);
    PostGetResDTO result = getPendingPost(post);
    //첨부 이미지 리스트 추가
    result.setFileInfos(fileInfos);
    return result;
  }

  /**
   * post 객체 생성
   * @param userNo
   * @param userName
   * @param title
   * @param content
   * @return
   */
  private Post createPendingPost(int userNo, String userName, String title, String content) {
    return Post.builder()
            .userNo(userNo)
            .userName(userName)
            .title(title)
            .content(content)
            .build();
  }

  /**
   * PostGetResDTO 객체 생성
   * @param post
   * @return
   */
  private PostGetResDTO getPendingPost(Post post) {
    return PostGetResDTO.builder()
            .userNo(post.getUserNo())
            .userName(post.getUserName())
            .title(post.getTitle())
            .content(post.getContent())
            .build();
  }
}
