package com.riseshine.pppboard.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.riseshine.pppboard.common.Constants;

public class CommonUtil {

  /**
   * DB에 민감정보 저장시 암호화하는 메소드
   * @param text 암호화할 텍스트
   * @return 암호화된 텍스트
   */
  public static String dbEncrypt(String text) {
    final Cipher encryptCipher;
    String result = "";
    try {
      encryptCipher = Cipher.getInstance("AES");
      encryptCipher.init(Cipher.ENCRYPT_MODE, generateAESKey(getSHA256(Constants.DB_CRYPTO_KEY), "UTF-8"));
      byte[] encryptedBytes = encryptCipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
      result = Base64.getEncoder().encodeToString(encryptedBytes);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  /**
   * DB에서 민감정보 복호화시 사용하는 메소드
   * @param text 복호화할 텍스트
   * @return 복호화된 텍스트
   */
  public static String dbDecrypt(String text) {
    final Cipher decryptCipher;
    String result;
    try {
      decryptCipher = Cipher.getInstance("AES");
      decryptCipher.init(Cipher.DECRYPT_MODE, generateAESKey(getSHA256(Constants.DB_CRYPTO_KEY), "UTF-8"));
      byte[] decryptedBytes = decryptCipher.doFinal(Base64.getDecoder().decode(text));
      result = new String(decryptedBytes, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  /**
   * AESKey 생성
   * @param key
   * @param encoding
   * @return
   */
  private static SecretKeySpec generateAESKey(final String key, final String encoding) {
    try {
      final byte[] finalKey = new byte[16];
      int i = 0;
      for (byte b : key.getBytes(encoding))
        finalKey[i++ % 16] ^= b;
      return new SecretKeySpec(finalKey, "AES");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * SHA256 가져옴
   * @param text
   * @return
   */
  private static String getSHA256(String text) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      byte[] hash = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for (byte b : hash) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
