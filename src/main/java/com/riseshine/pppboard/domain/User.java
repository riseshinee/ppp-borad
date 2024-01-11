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
public class User extends BaseEntity {
    /**
     * no
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    Integer no;

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

    /**
     * role collection
     */
    /*
    @ElementCollection(fetch = FetchType.EAGER) //roles 컬렉션
    @Builder.Default
    private List<String> roles = new ArrayList<>();
    */
    /**
     * 사용자의 권한 목록 리턴
     * @return
     */
    /*
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

     */
}
