package com.riseshine.pppboard.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
  //DB 암호화 키
  public static String DB_CRYPTO_KEY;
  @Value("${pppboard.db-crypto-key}")
  public void setDbCryptoKey(String dbCryptoKey) {
    DB_CRYPTO_KEY = dbCryptoKey;
  }
  public static String FILES_FOLDER_PATH = "upload_files/";
  public static String AWS_S3_CLOUDFRONT_URL = "https://pppboard.s3.ap-northeast-2.amazonaws.com/";
  public static String AWS_S3_BUCKET = "pppboard";

}
