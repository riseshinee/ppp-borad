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

  /**
   * 특정 postNo에 대해 seq를 자동으로 증가
   */
  @PostLoad
  private void prePersist() {
    if (postNo != null) {
      EntityManager entityManager = Persistence.createEntityManagerFactory("pppboard").createEntityManager();
      // 특정 postNo에 대한 마지막 seq 값을 조회
      Integer lastSeq = entityManager.createQuery(
                      "SELECT MAX(f.seq) FROM File f WHERE f.postNo = :postNo", Integer.class)
              .setParameter("postNo", postNo)
              .getSingleResult();

      // 마지막 seq 값이 null이면 1로 초기화, 아니면 +1
      seq = (lastSeq != null) ? lastSeq + 1 : 1;
    }
  }





}
