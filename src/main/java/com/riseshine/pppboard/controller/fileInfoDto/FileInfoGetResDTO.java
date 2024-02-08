package com.riseshine.pppboard.controller.fileInfoDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class FileInfoGetResDTO {
  private int seq;
  private String name;
  private String url;

  public FileInfoGetResDTO(int seq, String name, String url) {
    this.seq = seq;
    this.name = name;
    this.url = url;
  }
}

