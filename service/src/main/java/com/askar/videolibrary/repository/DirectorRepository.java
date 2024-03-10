package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Director;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class DirectorRepository extends BaseRepository<Long, Director> {

    public DirectorRepository(EntityManager entityManager) {
        super(Director.class, entityManager);
    }
}
