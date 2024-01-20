package com.riseshine.pppboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.riseshine.pppboard.common.Constants;
import com.riseshine.pppboard.common.utils.FileUtil;
import com.riseshine.pppboard.common.exception.CustomException;
import com.riseshine.pppboard.dao.FileInfoRepository;
import com.riseshine.pppboard.domain.FileInfo;
import com.riseshine.pppboard.provider.AwsS3Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FileService {
  private final FileInfoRepository fileInfoRepository;
  private final AwsS3Service awsS3Service;

  /**
   * 첨부파일 유효성 체크
   * @param files
   */
  public boolean validateFile(List<MultipartFile> files) {
    if( files.size() > 5) {
      throw new CustomException("이미지는 최대 5개까지 업로드 가능합니다.", HttpStatus.BAD_REQUEST);
    }

    for (MultipartFile file : files) {
      if (!FileUtil.isImageExtension(file.getOriginalFilename())) {
        throw new CustomException("지원하지 않는 이미지 형식입니다.", HttpStatus.BAD_REQUEST);
      }
    }
    return !files.isEmpty();
  }

  /**
   * 파일 업로드
   * @param postNo
   * @param files
   * @throws Exception
   */
  public void uploadFile(Integer postNo, List<MultipartFile> files) throws Exception  {
    int seq = 0;
    try {
      for (MultipartFile file : files) {
        String fileName = FileUtil.generateFileName(file.getOriginalFilename());
        //로컬 경로에 업로드
        File uploadFile = new File(Constants.FILES_FOLDER_PATH +fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
        fileOutputStream.write(uploadFile.toString().getBytes());
        fileOutputStream.close();
        //s3 bucket 업로드
        uploadS3(uploadFile, fileName);
        //db에 저장
        saveFile(postNo,++seq,fileName);
        //로컬 파일 삭제
        uploadFile.delete();
      }

    } catch (Exception e) {
      log.error("[FILE UPLOAD] failed:", e);
      throw new CustomException("파일 업로드 실패", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 업로드한 파일 정보를 db에 저장
   * @param postNo
   * @param seq
   * @param name
   */
  private void saveFile(Integer postNo, Integer seq, String name){
    FileInfo fileInfo = createPendingFileInfo(postNo,seq,name);
    fileInfoRepository.save(fileInfo);
  }

  /**
   * fileinfo 객체 생성
   * @param postNo
   * @param seq
   * @param name
   * @return
   */
  private FileInfo createPendingFileInfo(Integer postNo, Integer seq, String name) {
    return FileInfo.builder()
            .postNo(postNo)
            .seq(seq)
            .name(name)
            .build();
  }

  private void uploadS3(File file, String fileName) {
    try {
      String uploadPath = String.format("%s/%s/%s",
              FileUtil.getCurrentYear(),
              FileUtil.getCurrentMonth(),
              fileName);
      awsS3Service.uploadOriginFile(uploadPath, file);
    } catch (Exception e) {
      log.error("[AWS S3 UPLOAD] failed: {}", file.getName(), e);
      throw new CustomException("AWS S3 upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}