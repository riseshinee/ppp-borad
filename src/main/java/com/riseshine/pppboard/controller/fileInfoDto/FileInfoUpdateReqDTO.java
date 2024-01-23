package com.riseshine.pppboard.controller.fileInfoDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FileInfoUpdateReqDTO {
  /**
   * file no
   */
  int no;

  /**
   * 첨부 파일 순서
   */
  int seq;
}
