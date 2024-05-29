package com.riseshine.pppboard.service;

import com.riseshine.pppboard.common.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.riseshine.pppboard.common.exception.CustomException;
import com.riseshine.pppboard.domain.Post;
import com.riseshine.pppboard.controller.postDto.*;
import com.riseshine.pppboard.controller.fileInfoDto.FileInfoGetResDTO;
import com.riseshine.pppboard.dao.PostRepository;
import com.riseshine.pppboard.dao.PostListRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final PostListRepository postListRepository;
  private final FileInfoService fileinfoService;

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
  public PostGetResDTO getPost(int no) {
    Post post = postRepository.findFirstByNo(no).orElseThrow(() ->
                    new CustomException(ResultCode.POST_NOT_EXIST)
    );
    //첨부 이미지 리스트 return
    List<FileInfoGetResDTO> fileInfos = fileinfoService.getFilesByPostNo(no);
    PostGetResDTO result = getPendingPost(post);
    //첨부 이미지 리스트 추가
    result.setFileInfos(fileInfos);
    return result;
  }

  /**
   * 게시글 수정
   * @param no
   * @param title
   * @param content
   * @return
   */
  public int putPost(int no, String title, String content) {
    //게시글 업데이트
    postRepository.updateByNo(no,title,content);
    return no;
  }

  /**
   * 게시글 삭제 (delete 필드만 업데이트 함, 실제 삭제 X)
   * @param no
   */
  public void deletePost(int no) {
    postRepository.deleteByNo(no);
  }

  /**
   * 게시글 검색 및 리스트 조회
   * @param title
   * @param content
   * @param pageable
   * @return
   */
  public PostListGetResDTO getPostList(String title, String content, Pageable pageable) {

    Page<Post> result = postListRepository.getList(title,content,pageable);
    return PostListGetResDTO.builder()
            .postList(result.getContent())
            .totalCount(result.getTotalElements())
            .totalPage(result.getTotalPages())
            .build();

  }

  /**
   * post 객체 생성
   * @param userNo
   * @param userName
   * @param title
   * @param content
   * @return
   */
  protected Post createPendingPost(int userNo, String userName, String title, String content) {
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
  protected PostGetResDTO getPendingPost(Post post) {
    return PostGetResDTO.builder()
            .userNo(post.getUserNo())
            .userName(post.getUserName())
            .title(post.getTitle())
            .content(post.getContent())
            .build();
  }
}
