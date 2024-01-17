package com.riseshine.pppboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.riseshine.pppboard.service.UserService;
import com.riseshine.pppboard.service.PostService;
import com.riseshine.pppboard.controller.postDto.*;
import com.riseshine.pppboard.controller.userDto.UserGetResDTO;

@Slf4j
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Tag(name = "2. PostController", description = "게시글 관리 API")
public class PostController {
  private final UserService userService;
  private final PostService postService;
  @PostMapping("/{userNo}")
  @Operation(summary = "게시글 생성")
  public ResponseWrapper<Integer> savePost(@PathVariable("userNo") Integer userNo,  @RequestBody @Valid PostCraeteReqDTO post) throws Exception {
    //유저 유효성 체크
    UserGetResDTO findUser = userService.getUser(userNo);
    ResponseWrapper<Integer> responseWrapper = new ResponseWrapper<>();
    responseWrapper.setData(postService.savePost(userNo, findUser.getName(), post));
    return responseWrapper;
  }
}
