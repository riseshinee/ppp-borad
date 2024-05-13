package com.riseshine.pppboard.service;

import com.riseshine.pppboard.common.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.riseshine.pppboard.common.exception.CustomException;
import com.riseshine.pppboard.controller.commnetDto.*;
import com.riseshine.pppboard.dao.CommentRepository;
import com.riseshine.pppboard.domain.Comment;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  /**
   * 댓글 생성
   * @param createCommentDto
   * @return
   */
  public int saveComment(CommentCreateReqDTO createCommentDto, String userName) {
    checkParentValidated(createCommentDto.getParentNo());
    Comment comment = createPendingComment(createCommentDto, userName);
    return commentRepository.save(comment).getNo();

  }

  /**
   * 댓글 수정
   * @param no
   * @param content
   * @return
   */
  public int putComment(int no, String content) {
    commentRepository.updateByNo(no,
            content);
    return no;
  }

  /**
   * 댓글 삭제
   * @param no
   */
  public void deleteCommnetByNo(int no) {
    commentRepository.deleteByNo(no);
  }

  /**
   * 부모 댓글 리스트 조회
   * @param postNo
   * @return
   */
  public List<CommentGetResDTO> getCommentList(int postNo) {
    List<CommentGetResDTO> result = new ArrayList<>();
    commentRepository.findAllByPostNoAndParentNoIsNull(postNo).ifPresent(commnets -> {
      for( Comment comment : commnets){
        CommentGetResDTO commentDto = CommentGetResDTO.builder()
                .no(comment.getNo())
                .userName(comment.getUserName())
                .content(comment.getContent())
                .build();
        result.add(commentDto);
      }
    });
    return result;
  }

  /**
   * 댓글의 댓글 생성 유효성 체크
   * @param no
   */
  protected void checkParentValidated(int no) {
    commentRepository.findFirstByNo(no).ifPresent(comment -> {
      if(comment.getParentNo() != null && comment.getParentNo() > 0) {
        throw new CustomException(ResultCode.INTERNAL_SERVER_ERROR);
       // throw new CustomException("자식 댓글에는 댓글을 작성할 수 없습니다.", HttpStatus.BAD_REQUEST);
      }
    });
  }

  /**
   * comment 객체 생성
   * @param createCommentDto
   * @return
   */
  protected Comment createPendingComment(CommentCreateReqDTO createCommentDto, String userName){
    return Comment.builder()
            .parentNo(createCommentDto.getParentNo() > 0 ? createCommentDto.getParentNo() : null)
            .postNo(createCommentDto.getPostNo())
            .userNo(createCommentDto.getUserNo())
            .userName(userName)
            .content(createCommentDto.getContent())
            .build();
  }
}
