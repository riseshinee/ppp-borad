package com.riseshine.pppboard.dao;

import com.riseshine.pppboard.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileInfoRepository extends JpaRepository<FileInfo, Integer>, JpaSpecificationExecutor<FileInfo> {

  Optional<List<FileInfo>> findByPostNoOrderBySeqAsc(int postNo);
  Optional<FileInfo> findByNo(int no);
  FileInfo findFirstByPostNoOrderBySeqDesc(int postNo);
  int countAllByPostNo(int postNo);
  void deleteByNo(int no);
  @Modifying
  @Query(value = "UPDATE `file_info` SET seq= :seq WHERE no= :no", nativeQuery = true)
  void updateByNo(int no, int seq);


}
