package com.riseshine.pppboard.controller;

import com.riseshine.pppboard.service.FileInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
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
  private final FileInfoService fileService;
  @PostMapping(value = "/{userNo}", consumes = "multipart/form-data")
  @Operation(summary = "게시글 생성")
  public ResponseWrapper<Integer> savePost(@PathVariable("userNo") int userNo,
                                           @RequestParam("title") String title,
                                           @RequestParam("content") String content,
                                           @RequestParam("file") List<MultipartFile> file) {

    //첨부 파일 유효성 검증
    boolean checkFileValidated = fileService.validateFile(file);
    //유저 유효성 검증
    UserGetResDTO findUser = userService.getUser(userNo);
    //게시글 생성
    int postNo = postService.savePost(userNo, findUser.getName(), title, content);
    //첨부 이미지 업로드
    if (checkFileValidated) {
      fileService.uploadFilesByPostNo(postNo, file);
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

  @PutMapping("/{no}")
  @Operation(summary = "게시글 수정")
  public ResponseWrapper<Integer> putPost(@PathVariable("no") Integer no,
                                          @RequestParam("title") String title,
                                          @RequestParam("content") String content) {
    ResponseWrapper<Integer> responseWrapper = new ResponseWrapper<>();
    responseWrapper.setData(postService.putPost(no, title, content));
    return responseWrapper;
  }

  @DeleteMapping("/{no}")
  @Operation(summary = "게시글 삭제")
  public void deletePost(@PathVariable("no") Integer no) {
    postService.deletePost(no);
  }

  @GetMapping("")
  @Operation(summary = "게시글 리스트")
  public ResponseWrapper<PostListGetResDTO> getList(
          @RequestParam(value = "title", defaultValue = "") String title,
          @RequestParam(value = "content", defaultValue = "") String content,
          Pageable pageable) {
    ResponseWrapper<PostListGetResDTO> responseWrapper = new ResponseWrapper<>();
    responseWrapper.setData(postService.getPostList(title, content, pageable));
    return responseWrapper;
  }

}
