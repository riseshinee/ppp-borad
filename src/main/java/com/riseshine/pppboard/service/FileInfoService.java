package com.riseshine.pppboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.riseshine.pppboard.common.Constants;
import com.riseshine.pppboard.common.utils.FileUtil;
import com.riseshine.pppboard.common.exception.CustomException;
import com.riseshine.pppboard.dao.FileInfoRepository;
import com.riseshine.pppboard.domain.FileInfo;
import com.riseshine.pppboard.provider.AwsS3Service;
import com.riseshine.pppboard.controller.fileInfoDto.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FileInfoService {
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
   * 첨부파일 추가 시 유효성 검증
   * @param postNo
   * @param file
   */
  public void validateFileForAdd(int postNo, MultipartFile file) {
    int fileCnt = fileInfoRepository.countAllByPostNo(postNo);
    if(fileCnt > 5) {
      new CustomException("첨부 파일은 5개 이하입니다.", HttpStatus.BAD_REQUEST);
    }
    if (!FileUtil.isImageExtension(file.getOriginalFilename())) {
      throw new CustomException("지원하지 않는 이미지 형식입니다.", HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * 파일 업로드
   * @param postNo
   * @param files
   * @throws Exception
   */
  public void uploadFilesByPostNo(int postNo, List<MultipartFile> files) throws Exception  {
    try {
      for (MultipartFile file : files) {
        String fileName = FileUtil.generateFileName(file.getOriginalFilename());
        File uploadFile = createFileAndUploadToS3(file, fileName);
        //db에 저장
        saveFile(postNo,fileName);
        //로컬 파일 삭제
        uploadFile.delete();
      }
    } catch (Exception e) {
      log.error("[FILE UPLOAD] failed:", e);
      throw new CustomException("파일 업로드 실패", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * post_no를 기준으로 첨부파일 리스트 return
   * @param postNo
   * @return
   * @throws ParseException
   */
  public List<FileInfoGetResDTO> getFilesByPostNo(int postNo) {
    List<FileInfoGetResDTO> result = new ArrayList<>();
    fileInfoRepository.findByPostNoOrderBySeqAsc(postNo).ifPresent(fileInfos -> {
      for(FileInfo fileInfo: fileInfos) {
        try {
          FileInfoGetResDTO fileInfoDTO = getPendingFileInfo(fileInfo);
          result.add(fileInfoDTO);
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
      }
    });
    return result;
  }

  /**
   * 업로드 파일 삭제
   * @param no
   */
  public void deleteFileByNo(int no) {
    FileInfo fileInfo = fileInfoRepository.findByNo(no).orElseThrow(() ->
            new CustomException("파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
    try {
      String filePath = FileUtil.getFilePath(fileInfo.getCreatedAt(), fileInfo.getName());
      //S3 버킷에서 제거
      awsS3Service.deleteOriginFile(filePath);
      //db에서 제거
      fileInfoRepository.deleteByNo(no);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 첨부파일 추가
   * @param postNo
   * @param file
   */
  public void addFileByPostNo(int postNo, MultipartFile file) {
    //첨부파일 유효성 검증
    validateFileForAdd(postNo,file);
    try {
      String fileName = FileUtil.generateFileName(file.getOriginalFilename());
      //로컬 경로에 업로드
      File uploadFile = createFileAndUploadToS3(file, fileName);
      //db에 저장
      saveFile(postNo,fileName);
      //로컬 파일 삭제
      uploadFile.delete();
    } catch (Exception e) {
      log.error("[FILE UPLOAD] failed:", e);
      throw new CustomException("파일 업로드 실패", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private File createFileAndUploadToS3(MultipartFile file, String fileName) {
    try {
      File uploadFile = new File(Constants.FILES_FOLDER_PATH + fileName);
      FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
      fileOutputStream.write(uploadFile.toString().getBytes());
      fileOutputStream.close();
      uploadS3(uploadFile, fileName);
      return uploadFile;
    } catch (Exception e) {
      log.error("[CREATE FILE STREAM] failed:", e);
      throw new CustomException("파일 스트림 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  /**
   * 업로드한 파일 정보를 db에 저장
   * @param postNo
   * @param name
   */
  private void saveFile(int postNo, String name){
    FileInfo fileInfo = createPendingFileInfo(postNo,name);
    fileInfoRepository.save(fileInfo);
  }

  /**
   * fileinfo 객체 생성
   * @param postNo
   * @param name
   * @return
   */
  private FileInfo createPendingFileInfo(int postNo, String name) {
    return FileInfo.builder()
            .postNo(postNo)
            .name(name)
            .build();
  }

  /**
   * FileInfoGetResDTO 객체 생성
   * @param fileinfo
   * @return
   * @throws ParseException
   */
  private FileInfoGetResDTO getPendingFileInfo(FileInfo fileinfo) throws ParseException {
    return FileInfoGetResDTO.builder()
            .seq(fileinfo.getSeq())
            .name(fileinfo.getName())
            .url(FileUtil.getFileUrl(fileinfo.getCreatedAt(),fileinfo.getName()))
            .build();
  }

  /**
   * 이미지를 S3 버킷에 업로드
   * @param file
   * @param fileName
   */
  private void uploadS3(File file, String fileName) {
    String uploadPath = String.format("%s/%s/%s",
            FileUtil.getCurrentYear(),
            FileUtil.getCurrentMonth(),
            fileName);
    awsS3Service.uploadOriginFile(uploadPath, file);
  }

}