package com.askar.videolibrary.dao;

import com.askar.videolibrary.entity.Actor;
import jakarta.persistence.EntityManager;

public class ActorDao extends BaseDao<Long, Actor> {
    public ActorDao(EntityManager entityManager) {
        super(Actor.class, entityManager);
    }
}
