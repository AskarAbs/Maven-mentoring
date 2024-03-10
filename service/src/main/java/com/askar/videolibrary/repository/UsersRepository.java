package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Users;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UsersRepository extends BaseRepository<Long, Users> {

    public UsersRepository(EntityManager entityManager) {
        super(Users.class, entityManager);
    }
}
