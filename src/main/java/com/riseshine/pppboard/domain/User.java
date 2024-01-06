package com.riseshine.pppboard.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Builder
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    /**
     * no
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    String no;

    /**
     * User Id
     */
    @Column(name = "id")
    String id;

    /**
     * 유저 이름
     */
    @Column(name = "name")
    String name;

    /**
     * 비밀번호
     */
    @Column(name = "password")
    String passsword;

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
