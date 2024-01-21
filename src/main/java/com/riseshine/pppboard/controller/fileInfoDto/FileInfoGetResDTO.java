package com.riseshine.pppboard.controller.fileInfoDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class FileInfoGetResDTO {
  int seq;
  String name;
  String url;
}

