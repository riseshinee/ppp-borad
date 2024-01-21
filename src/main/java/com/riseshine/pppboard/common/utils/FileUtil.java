package com.riseshine.pppboard.common.utils;

import com.riseshine.pppboard.common.Constants;
import com.riseshine.pppboard.common.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class FileUtil {
  private static final String[] IMAGE_EXT = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
  /**
   * 파일 확장자 검증
   * @param originName
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
   * 생성시간, 파일명을 이용하여 첨부 이미지 URL return
   * @param createdAt
   * @param fileName
   * @return
   * @throws ParseException
   */
  public static String getFileUrl(String createdAt, String fileName) throws ParseException {
    String yearMonth = getFileYearMonth(createdAt);
    return Constants.AWS_S3_CLOUDFRONT_URL + yearMonth + "/" + fileName;
  }

  /**
   * 현재 년도
   * @return
   */
  public static String getCurrentYear() {
    LocalDate currentDate = LocalDate.now();
    return String.valueOf(currentDate.getYear());
  }

  /**
   * 현재 월
   * @return
   */
  public static String getCurrentMonth() {
    LocalDate currentDate = LocalDate.now();
    return String.valueOf(currentDate.getMonthValue());
  }

  /**
   * 파일명 확장자 추출
   * @param originName
   * @return
   */
  private static String getFileExtension(String originName){
    return Objects.requireNonNull(originName).substring(originName.lastIndexOf("."));
  }

  /**
   * 생성시간을 기준으로 yyyy/m으로 return
   * @param inputDateTime
   * @return
   * @throws ParseException
   */
  private static String getFileYearMonth(String inputDateTime) throws ParseException {
    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/M");
    Date date = inputFormat.parse(inputDateTime);
    return outputFormat.format(date);
  }
}
