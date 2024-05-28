package com.riseshine.pppboard.provider;

import com.riseshine.pppboard.common.Constants;
import com.riseshine.pppboard.common.ResultCode;
import com.riseshine.pppboard.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.FileUpload;
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;
  private final S3Client s3Client;
  private final S3TransferManager s3TransferManager;

  /**
   * S3 파일 업로드
   *
   * @param key  파일 업로드 경로
   * @param file 업로드할 파일
   * @return
   */
  public void uploadOriginFile(String key, File file) {
    try {
      UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
              .putObjectRequest(builder -> builder.bucket(bucket).key(key))
              .source(file)
              .build();
      FileUpload fileUpload = s3TransferManager.uploadFile(uploadFileRequest);
      fileUpload.completionFuture().join();
    } catch (S3Exception e) {
      log.error("[AWS S3 UPLOAD] failed: {}", file.getName(), e);
      throw new CustomException(ResultCode.AWS_S3_UPLOAD_FAILED);
    }
  }

  public void deleteOriginFile(String filePath) {
    try {
      DeleteObjectRequest request = DeleteObjectRequest.builder()
              .bucket(Constants.AWS_S3_BUCKET)
              .key(filePath)
              .build();
      s3Client.deleteObject(request);
    } catch (S3Exception e){
      log.error("[AWS S3 DELETE] failed: {}", filePath, e);
      throw new CustomException(ResultCode.AWS_S3_DELETE_FAILED);
    }
  }
}
