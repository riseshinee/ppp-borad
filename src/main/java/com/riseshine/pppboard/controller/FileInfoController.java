package com.riseshine.pppboard.controller;

import com.riseshine.pppboard.controller.userDto.UserUpdateReqDTO;
import com.riseshine.pppboard.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.riseshine.pppboard.service.FileInfoService;

@Slf4j
@RestController
@RequestMapping("/api/fileInfo")
@RequiredArgsConstructor
@Tag(name = "99. FileInfoController", description = "첨부 파일 관리 API")
public class FileInfoController {
  private final FileInfoService fileInfoService;

  @DeleteMapping("/{no}")
  @Operation(summary = "첨부 파일 삭제")
  public void deleteFile(@PathVariable("no") int no) {
    fileInfoService.deleteFileByNo(no);
  }

}
