package com.riseshine.pppboard.controller;

import com.riseshine.pppboard.controller.userDto.UserGetResDTO;
import com.riseshine.pppboard.service.UserService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.riseshine.pppboard.service.CommentService;
import com.riseshine.pppboard.controller.commnetDto.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Tag(name = "3. CommnetController", description = "댓글 관리 API")
public class CommentController {

  private final CommentService commentService;
  private final UserService userService;

  @PostMapping()
  @Operation(summary = "댓글 생성")
  public ResponseWrapper<Integer> saveComment(@RequestBody @Valid CommentCreateReqDTO comment) {
    UserGetResDTO findUser = userService.getUser(comment.getUserNo());
    ResponseWrapper<Integer> responseWrapper = new ResponseWrapper<>();
    responseWrapper.setData(commentService.saveComment(comment,findUser.getName()));
    return responseWrapper;
  }

  @PutMapping("/{no}")
  @Operation(summary = "댓글수정")
  public ResponseWrapper<Integer> putCommnet(@PathVariable("no") int no, @RequestParam("content") String content) {
    ResponseWrapper<Integer> responseWrapper = new ResponseWrapper<>();
    responseWrapper.setData(commentService.putComment(no, content));
    return responseWrapper;
  }

  @DeleteMapping("/{no}")
  @Operation(summary = "댓글 삭제")
  public void deleteCommnet(@PathVariable("no") int no) {
    commentService.deleteCommnetByNo(no);
  }

  @GetMapping("/{postNo}")
  @Operation(summary = "댓글조회")
  public ResponseWrapper<List<CommentGetResDTO>> getCommnet(@PathVariable("postNo") int postNo) {
    ResponseWrapper<List<CommentGetResDTO>> responseWrapper = new ResponseWrapper<>();
    responseWrapper.setData(commentService.getCommentList(postNo));
    return responseWrapper;
  }
}
