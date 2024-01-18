package com.riseshine.pppboard.common.utils;

import com.riseshine.pppboard.common.exception.CustomException;
import org.springframework.http.HttpStatus;
import java.util.Objects;
import java.util.UUID;

public class FileUtil {
  private static final String[] IMAGE_EXT = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
  /**
   * 파일 확장자 검증
   * @param extension
   * @return
   */
  public static boolean isImageExtension(String originName) {
    String fileExtension = getFileExtension(originName);
    for (String supportedExtension : IMAGE_EXT) {
      if (supportedExtension.equalsIgnoreCase(fileExtension)) {
        return true; // 이미지 확장자에 속하는 경우 true 반환
      }
    }
    return false; // 이미지 확장자에 속하지 않는 경우 false 반환
  }

  /**
   * 파일명을 유니크하게 생성
   * @param originName
   * @return
   */
  public static String generateFileName(String originName){
    if(originName == null){
      throw new CustomException("파일명이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
    }
    //파일 확장자 추출
    String fileExtension = getFileExtension(originName);
    //랜덤 파일명 + 확장자
    return UUID.randomUUID() + fileExtension;
  }

  /**
   * 파일명 확장자 추출
   * @param originName
   * @return
   */
  private static String getFileExtension(String originName){
    return Objects.requireNonNull(originName).substring(originName.lastIndexOf("."));
  }

}
