package com.riseshine.pppboard;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

@Configuration
public class AwsConfig {
  @Value("${cloud.aws.credentials.access-key}")
  private String accessKey;

  @Value("${cloud.aws.credentials.secret-key}")
  private String secretKey;

  @Value("${cloud.aws.region.static}")
  private String region;

  @Bean
  public S3Client s3Client() {
    return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(getCredentials()))
            .build();
  }

  @Bean
  public S3TransferManager s3TransferManager() {
    S3AsyncClient s3AsyncClient = S3AsyncClient.crtBuilder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(getCredentials()))
            .build();

    return S3TransferManager.builder()
            .s3Client(s3AsyncClient)
            .build();
  }

  private AwsBasicCredentials getCredentials(){
    return AwsBasicCredentials.create(accessKey, secretKey);
  }
}


