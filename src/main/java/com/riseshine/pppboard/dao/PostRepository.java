package com.riseshine.pppboard.dao;

import com.riseshine.pppboard.domain.FileInfo;
import com.riseshine.pppboard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
  Optional<Post> findFirstByNo(int no);

  @Modifying
  @Query(value = "UPDATE `Post` SET title= :title, content= :content WHERE no= :no", nativeQuery = true)
  void updateByNo(int no, String title, String content);

  @Modifying
  @Query(value = "UPDATE `Post` SET delete_yn = 1, deleted_at = NOW() WHERE no= :no", nativeQuery = true)
  void deleteByNo(int no);

}
