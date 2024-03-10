package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Review;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepository extends BaseRepository<Long, Review> {

    public ReviewRepository(EntityManager entityManager) {
        super(Review.class, entityManager);
    }
}
