package com.riseshine.pppboard.service;

import com.riseshine.pppboard.common.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
      throw new CustomException(ResultCode.FILE_ATTACH_COUNT);
    }
    for (MultipartFile file : files) {
      if (!FileUtil.isImageExtension(file.getOriginalFilename())) {
        throw new CustomException(ResultCode.FILE_TYPE_INVALID);
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
      throw new CustomException(ResultCode.FILE_ATTACH_COUNT);
    }
    if (!FileUtil.isImageExtension(file.getOriginalFilename())) {
      throw new CustomException(ResultCode.FILE_TYPE_INVALID);
    }
  }

  /**
   * 파일 업로드
   * @param postNo
   * @param files
   * @throws Exception
   */
  public void uploadFilesByPostNo(int postNo, List<MultipartFile> files) {
    try {
      //다중파일은 seq를 자동으로 증가시킴, 단일파일은 마지막 seq를 기준으로 증가
      int seq = files.size() > 1 ? 0 : getLastSeqByPostNo(postNo);
      for (MultipartFile file : files) {
        String fileName = FileUtil.generateFileName(file.getOriginalFilename());
        File uploadFile = createFileAndUploadToS3(file, fileName);
        //db에 저장
        saveFile(postNo,++seq,fileName);
        //로컬 파일 삭제
        uploadFile.delete();
      }
    } catch (Exception e) {
      log.error("[FILE UPLOAD] failed:", e);
      throw new CustomException(ResultCode.FILE_UPLOAD_FAILED);
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
                    new CustomException(ResultCode.FILE_NOT_EXIST)
    );
    try {
      String filePath = FileUtil.getFilePath(fileInfo.getCreatedAt(), fileInfo.getName());
      awsS3Service.deleteOriginFile(filePath);
      //db에서 제거
      fileInfoRepository.deleteByNo(no);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 첨부파일 순서 변경
   * @param list updateFileInfos
   */
  public void updateFileInfo( List<FileInfoUpdateReqDTO> updateFileInfos) {
    Set<Integer> seqSet = new HashSet<>();
    for (FileInfoUpdateReqDTO fileInfo : updateFileInfos ){
      if (!seqSet.add(fileInfo.getSeq())) {
        throw new CustomException(ResultCode.FILE_SEQ_DUPLICATED);
      }
      fileInfoRepository.updateByNo(fileInfo.getNo(), fileInfo.getSeq());
    }
  }

  /**
   * 파일 스트림 생성, S3 버킷에 업로드
   * @param file
   * @param fileName
   * @return
   */
  protected File createFileAndUploadToS3(MultipartFile file, String fileName) {
    try {
      File uploadFile = new File(Constants.FILES_FOLDER_PATH + fileName);
      FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
      fileOutputStream.write(file.getBytes());
      fileOutputStream.close();
      uploadS3(uploadFile, fileName);
      return uploadFile;
    } catch (Exception e) {
      log.error("[UPLOAD FILE] failed:", e);
      throw new CustomException(ResultCode.FILE_UPLOAD_FAILED);
    }
  }

  /**
   * 업로드한 파일 정보를 db에 저장
   * @param postNo
   * @param name
   */
  protected void saveFile(int postNo, int seq, String name){
    FileInfo fileInfo = createPendingFileInfo(postNo, seq, name);
    fileInfoRepository.save(fileInfo);
  }

  /**
   * fileinfo 객체 생성
   * @param postNo
   * @param name
   * @return
   */
  protected FileInfo createPendingFileInfo(int postNo, int seq, String name) {
    return FileInfo.builder()
            .postNo(postNo)
            .seq(seq)
            .name(name)
            .build();
  }

  /**
   * FileInfoGetResDTO 객체 생성
   * @param fileinfo
   * @return
   * @throws ParseException
   */
  protected FileInfoGetResDTO getPendingFileInfo(FileInfo fileinfo) throws ParseException {
    return FileInfoGetResDTO.builder()
            .seq(fileinfo.getSeq())
            .name(fileinfo.getName())
            .url(FileUtil.getFileUrl(fileinfo.getCreatedAt(),fileinfo.getName()))
            .build();
  }

  /**
   * 마지막 seq를 가져옴
   * @param postNo
   * @return
   * @throws Exception
   */
  protected int getLastSeqByPostNo(int postNo) {
    return fileInfoRepository.findFirstByPostNoOrderBySeqDesc(postNo).getSeq();
  }

  /**
   * 이미지를 S3 버킷에 업로드
   * @param file
   * @param fileName
   */
  protected void uploadS3(File file, String fileName) {
    String uploadPath = String.format("%s/%s/%s",
            FileUtil.getCurrentYear(),
            FileUtil.getCurrentMonth(),
            fileName);
    awsS3Service.uploadOriginFile(uploadPath, file);
  }

}
