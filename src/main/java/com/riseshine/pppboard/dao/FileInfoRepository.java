package com.riseshine.pppboard.dao;

import com.riseshine.pppboard.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileInfoRepository extends JpaRepository<FileInfo, Integer>, JpaSpecificationExecutor<FileInfo> {

}
