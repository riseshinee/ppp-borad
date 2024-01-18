package com.riseshine.pppboard.dao;

import com.riseshine.pppboard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

}
