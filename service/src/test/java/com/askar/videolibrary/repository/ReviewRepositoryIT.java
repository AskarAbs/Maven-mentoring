package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Review;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewRepositoryIT extends IntegrationTestBase{

    private static final Long REVIEW_ID = 1L;

    @Test
    void findAll() {
        var reviews = reviewRepository.findAll();

        assertThat(reviews).hasSize(2);
    }

    @Test
    void save() {
        var review = createReview();

        var actualReview = reviewRepository.save(review);

        assertThat(actualReview.getId()).isNotNull();
    }


    @Test
    void update() {
        var review = reviewRepository.findById(REVIEW_ID);
        assertThat(review).isPresent();
        review.ifPresent(reviews -> reviews.setEvaluation(1));

        review.ifPresent(reviews -> reviewRepository.update(reviews));
        entityManager.flush();
        var actualReview = reviewRepository.findById(review.get().getId());

        assertThat(actualReview).isPresent();
        assertThat(actualReview.get().getEvaluation()).isEqualTo(1);
        assertThat(actualReview.get().getId()).isEqualTo(REVIEW_ID);
    }

    @Test
    void delete() {
        var review = reviewRepository.findById(REVIEW_ID);

        review.ifPresent(value -> reviewRepository.delete(value));

        assertThat(reviewRepository.findById(REVIEW_ID)).isEmpty();
    }

    @Test
    void findById() {
        var review = reviewRepository.findById(REVIEW_ID);

        assertThat(review).isPresent();
        assertThat(review.get().getId()).isEqualTo(REVIEW_ID);
    }

    public Review createReview() {
        return Review.builder()
                .createdAt(Instant.now())
                .evaluation(5)
                .text("Good film")
                .build();
    }
}