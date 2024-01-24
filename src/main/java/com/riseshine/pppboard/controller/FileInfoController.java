package com.riseshine.pppboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.riseshine.pppboard.service.FileInfoService;
import com.riseshine.pppboard.controller.fileInfoDto.FileInfoUpdateReqDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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

  @PostMapping(value = "/{postNo}", consumes = "multipart/form-data")
  @Operation(summary = "첨부 파일 추가")
  public void addFile(@PathVariable("postNo") int postNo,
                     @RequestParam("file") MultipartFile file) {
    fileInfoService.validateFileForAdd(postNo,file);
    List<MultipartFile> files = new ArrayList<>();
    files.add(file);
    fileInfoService.uploadFilesByPostNo(postNo, files);
  }

  @PutMapping("/{postNo}")
  @Operation(summary = "첨부 파일 순서 업데이트")
  public void updateFile(@RequestBody List<FileInfoUpdateReqDTO> fileInfo ) {
    //fileInfoService.deleteFileByNo(no);
  }

}
