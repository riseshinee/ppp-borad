package com.riseshine.pppboard.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.FileUpload;
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest;

import java.io.File;

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
    UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
            .putObjectRequest(builder -> builder.bucket(bucket).key(key))
            .source(file)
            .build();
    FileUpload fileUpload = s3TransferManager.uploadFile(uploadFileRequest);
    fileUpload.completionFuture().join();
  }
}
