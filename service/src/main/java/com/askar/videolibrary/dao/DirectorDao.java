package com.askar.videolibrary.dao;

import com.askar.videolibrary.entity.Director;
import jakarta.persistence.EntityManager;

public class DirectorDao extends BaseDao<Long, Director> {
    public DirectorDao(EntityManager entityManager) {
        super(Director.class, entityManager);
    }
}
