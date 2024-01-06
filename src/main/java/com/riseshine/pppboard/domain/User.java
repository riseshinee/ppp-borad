package com.riseshine.pppboard.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    /**
     * User Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11)
    private String id;

    /**
     * 유저 이름
     */
    @Column(name = "name", length = 10)
    String name;

    /**
     * 비밀번호
     */
    @Column(name = "password", length = 10)
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
