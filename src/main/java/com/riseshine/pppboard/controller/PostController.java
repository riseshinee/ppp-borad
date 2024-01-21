package com.riseshine.pppboard.controller;

import com.riseshine.pppboard.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.riseshine.pppboard.service.UserService;
import com.riseshine.pppboard.service.PostService;
import com.riseshine.pppboard.controller.userDto.UserGetResDTO;
import com.riseshine.pppboard.controller.postDto.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Tag(name = "2. PostController", description = "게시글 관리 API")
public class PostController {
  private final UserService userService;
  private final PostService postService;
  private final FileService fileService;
  @PostMapping(value = "/{userNo}", consumes = "multipart/form-data")
  @Operation(summary = "게시글 생성")
  public ResponseWrapper<Integer> savePost(@PathVariable("userNo") int userNo,
                                           @RequestParam("title") String title,
                                           @RequestParam("content") String content,
                                           @RequestParam("file") List<MultipartFile> file) throws Exception {

    //첨부 파일 유효성 검증
    boolean checkFileValidated = fileService.validateFile(file);
    //유저 유효성 검증
    UserGetResDTO findUser = userService.getUser(userNo);
    //게시글 생성
    int postNo = postService.savePost(userNo, findUser.getName(), title, content);
    //첨부 이미지 업로드
    if (checkFileValidated) {
      fileService.uploadFile(postNo, file);
    }
    ResponseWrapper<Integer> responseWrapper = new ResponseWrapper<>();
    responseWrapper.setData(postNo);
    return responseWrapper;
  }

  @GetMapping("/{no}")
  @Operation(summary = "게시글 조회")
  public ResponseWrapper<PostGetResDTO> getPost(@PathVariable("no") int no) throws Exception {
    ResponseWrapper<PostGetResDTO> responseWrapper = new ResponseWrapper<>();
    responseWrapper.setData(postService.getPost(no));
    return responseWrapper;
  }
}
