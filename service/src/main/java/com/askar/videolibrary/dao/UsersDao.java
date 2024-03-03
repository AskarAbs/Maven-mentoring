package com.askar.videolibrary.dao;

import com.askar.videolibrary.entity.Users;
import jakarta.persistence.EntityManager;

public class UsersDao extends BaseDao<Long, Users> {
    public UsersDao(EntityManager entityManager) {
        super(Users.class, entityManager);
    }
}
