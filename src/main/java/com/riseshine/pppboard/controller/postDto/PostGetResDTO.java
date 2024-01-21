package com.riseshine.pppboard.controller.postDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import com.riseshine.pppboard.controller.fileInfoDto.FileInfoGetResDTO;

@Setter
@Getter
@Builder
public class PostGetResDTO {
  int userNo;
  String userName;
  String title;
  String content;
  List<FileInfoGetResDTO> fileInfos;
  public void setFileInfos(List<FileInfoGetResDTO> fileInfos) {
    this.fileInfos = fileInfos;
  }
}

