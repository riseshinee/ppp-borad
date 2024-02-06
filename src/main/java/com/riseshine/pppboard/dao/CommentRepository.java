package com.riseshine.pppboard.dao;

import com.riseshine.pppboard.domain.Comment;
import com.riseshine.pppboard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {
  Optional<Comment> findFirstByNo(int no);
}
