package com.riseshine.pppboard.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
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
public class Comment extends BaseEntity {
  /**
   * no
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "no")
  Integer no;

  /**
   * 부모 댓글 PK
   */
  @Column(name = "parent_no")
  Integer parentNo;

  /**
   * 게시글 PK
   */
  @Column(name = "post_no")
  Integer postNo;

  /**
   * User PK
   */
  @Column(name = "user_no")
  Integer userNo;

  /**
   * 유저 이름
   */
  @Column(name = "user_name")
  String userName;

  /**
   * 내용
   */
  @Column(name = "content")
  String content;


}
