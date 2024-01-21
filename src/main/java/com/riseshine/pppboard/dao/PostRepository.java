package com.riseshine.pppboard.dao;

import com.riseshine.pppboard.domain.FileInfo;
import com.riseshine.pppboard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
  Optional<Post> findFirstByNo(int no);
}
