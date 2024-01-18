package com.riseshine.pppboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.riseshine.pppboard.service.UserService;
import com.riseshine.pppboard.service.PostService;
import com.riseshine.pppboard.controller.postDto.*;
import com.riseshine.pppboard.controller.userDto.UserGetResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Tag(name = "2. PostController", description = "게시글 관리 API")
public class PostController {
  private final UserService userService;
  private final PostService postService;
  @PostMapping(value = "/{userNo}", consumes = "multipart/form-data")
  @Operation(summary = "게시글 생성")
  public ResponseWrapper<Integer> savePost(@PathVariable("userNo") Integer userNo,
                                           @RequestPart PostCraeteReqDTO post,
                                           @RequestPart("file") MultipartFile file) throws Exception {

    log.info(String.valueOf(post));
    //유저 유효성 체크
    UserGetResDTO findUser = userService.getUser(userNo);
    ResponseWrapper<Integer> responseWrapper = new ResponseWrapper<>();
    Integer postNo = postService.savePost(userNo, findUser.getName(), post);
    if (!file.isEmpty()) {
      // 파일 업로드 로직 수행
      // ...
    }
    responseWrapper.setData(postNo);
    return responseWrapper;
  }
}
