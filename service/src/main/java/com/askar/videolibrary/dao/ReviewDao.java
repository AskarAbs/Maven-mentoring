package com.askar.videolibrary.dao;

import com.askar.videolibrary.entity.Review;
import jakarta.persistence.EntityManager;

public class ReviewDao extends BaseDao<Long, Review> {
    public ReviewDao(EntityManager entityManager) {
        super(Review.class, entityManager);
    }
}
