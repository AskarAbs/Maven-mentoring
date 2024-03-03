package com.askar.videolibrary.dao;

import com.askar.videolibrary.entity.FilmActor;
import jakarta.persistence.EntityManager;

public class FilmActorDao extends BaseDao<Long, FilmActor> {
    public FilmActorDao(EntityManager entityManager) {
        super(FilmActor.class, entityManager);
    }
}
