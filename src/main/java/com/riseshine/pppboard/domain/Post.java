package com.riseshine.pppboard.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Builder
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {
  /**
   * no
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "no")
  Integer no;

  /**
   * User no
   */
  @Column(name = "user_no")
  Integer userNo;

  /**
   * 유저 이름
   */
  @Column(name = "user_name")
  String userName;

  /**
   * 제목
   */
  @Column(name = "title")
  String title;

  /**
   * 비밀번호
   */
  @Column(name = "content")
  String content;

  /**
   * 삭제여부 (0: 삭제안됨, 1:삭제됨)
   */
  @Column(name = "delete_yn")
  Integer deleteYn;

  /**
   * 생성 시간
   */
  @Column(name = "created_at", columnDefinition = "DATETIME")
  String createdAt;

  /**
   * 업데이트 시간
   */
  @Column(name = "updated_at", columnDefinition = "DATETIME")
  String updatedAt;

  /**
   * 삭제 시간
   */
  @Column(name = "deleted_at", columnDefinition = "DATETIME")
  String deletedAt;

}
