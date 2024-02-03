package com.riseshine.pppboard.domain;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import java.io.Serializable;
import java.time.LocalDateTime;
@Getter
public abstract class BaseEntity implements Serializable {

    /**
     * Base Entity
     */
    private static final long serialVersionUID = 1L;

    /**
     * 생성 시간
     */
    @Column(name = "created_at", columnDefinition = "DATETIME")
    LocalDateTime createdAt;

    /**
     * 업데이트 시간
     */
    @Column(name = "updated_at", columnDefinition = "DATETIME")
    LocalDateTime updatedAt;

}
