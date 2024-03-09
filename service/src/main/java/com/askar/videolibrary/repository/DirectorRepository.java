package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Director;
import jakarta.persistence.EntityManager;

public class DirectorRepository extends BaseRepository<Long, Director> {

    public DirectorRepository(EntityManager entityManager) {
        super(Director.class, entityManager);
    }
}
