package com.riseshine.pppboard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.riseshine.pppboard.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
  User findFirstById(String id);

  User findFirstByNo(Integer no);

  @Modifying
  @Query(value = "UPDATE `User` SET name= :name, password= :password WHERE no= :no", nativeQuery = true)
  void updateByNo(Integer no, String name, String password);

}
