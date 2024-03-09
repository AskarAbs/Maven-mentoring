package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Users;
import jakarta.persistence.EntityManager;

public class UsersRepository extends BaseRepository<Long, Users> {

    public UsersRepository(EntityManager entityManager) {
        super(Users.class, entityManager);
    }
}
