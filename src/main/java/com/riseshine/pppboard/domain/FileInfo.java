package com.riseshine.pppboard.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Builder
@Getter
@Table(name = "file")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo extends BaseEntity{
  /**
   * no
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "no")
  Integer no;

  /**
   * post no
   */
  @Column(name = "post_no")
  Integer postNo;

  /**
   * 순서
   */
  @Column(name = "seq")
  Integer seq;

  /**
   * 파일명
   */
  @Column(name = "name")
  String name;

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

}
