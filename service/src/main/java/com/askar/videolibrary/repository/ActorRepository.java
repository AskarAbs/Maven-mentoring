package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Actor;
import jakarta.persistence.EntityManager;

public class ActorRepository extends BaseRepository<Long, Actor> {

    public ActorRepository(EntityManager entityManager) {
        super(Actor.class, entityManager);
    }
}
