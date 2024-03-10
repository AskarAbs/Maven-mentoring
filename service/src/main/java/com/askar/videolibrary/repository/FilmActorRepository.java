package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.FilmActor;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class FilmActorRepository extends BaseRepository<Long, FilmActor> {

    public FilmActorRepository(EntityManager entityManager) {
        super(FilmActor.class, entityManager);
    }
}
