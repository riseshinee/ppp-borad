package com.riseshine.pppboard.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {

    /**
     * Base Entity
     */
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    protected EntityManager entityManager;

}
