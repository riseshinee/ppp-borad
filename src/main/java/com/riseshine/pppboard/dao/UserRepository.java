package com.riseshine.pppboard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.riseshine.pppboard.domain.User;
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
  User findFirstById(String id);

}
