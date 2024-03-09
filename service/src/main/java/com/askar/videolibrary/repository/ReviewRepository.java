package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Review;
import jakarta.persistence.EntityManager;

public class ReviewRepository extends BaseRepository<Long, Review> {

    public ReviewRepository(EntityManager entityManager) {
        super(Review.class, entityManager);
    }
}
