package com.riseshine.pppboard.dao;

import com.riseshine.pppboard.domain.Comment;
import com.riseshine.pppboard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {
  Optional<Comment> findFirstByNo(int no);

  @Modifying
  @Query(value = "UPDATE `comment` SET content= :content WHERE no= :no", nativeQuery = true)
  void updateByNo(int no, String content);
}
