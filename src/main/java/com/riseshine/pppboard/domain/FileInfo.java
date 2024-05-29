package com.riseshine.pppboard.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Slf4j
@Entity
@Builder
@Getter
@Table(name = "file_info")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo extends BaseEntity {
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


}
