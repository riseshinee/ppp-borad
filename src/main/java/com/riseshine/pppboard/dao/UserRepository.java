package com.riseshine.pppboard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.riseshine.pppboard.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
  //아이디 기준으로 유저 정보 조회
  Optional<User> findFirstById(String id);

  //pk 기준으로 유저 정보 조회
  Optional<User> findFirstByNo(Integer no);

  //유저 정보 업데이트
  @Modifying
  @Query(value = "UPDATE `User` SET name= :name, password= :password WHERE no= :no", nativeQuery = true)
  void updateByNo(Integer no, String name, String password);

}
