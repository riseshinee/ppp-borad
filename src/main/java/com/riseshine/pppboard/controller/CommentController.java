package com.riseshine.pppboard.controller;

import com.riseshine.pppboard.controller.userDto.UserGetResDTO;
import com.riseshine.pppboard.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.riseshine.pppboard.service.CommentService;
import com.riseshine.pppboard.controller.commnetDto.*;

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

}
