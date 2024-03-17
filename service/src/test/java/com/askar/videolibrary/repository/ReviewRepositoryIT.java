package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Review;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class ReviewRepositoryIT extends IntegrationTestBase {

    private final ReviewRepository reviewRepository;

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
        var createdReview = createReview();
        var savedReview = reviewRepository.save(createdReview);

        var review = reviewRepository.findById(savedReview.getId());
        assertThat(review).isPresent();
        review.ifPresent(reviews -> reviews.setEvaluation(1));

        review.ifPresent(reviewRepository::update);
        entityManager.flush();
        var actualReview = reviewRepository.findById(review.get().getId());

        assertThat(actualReview).isPresent();
        assertThat(actualReview.get().getEvaluation()).isEqualTo(1);
        assertThat(actualReview.get().getId()).isEqualTo(savedReview.getId());
    }

    @Test
    void delete() {
        var createdReview = createReview();
        var savedReview = reviewRepository.save(createdReview);
        var review = reviewRepository.findById(savedReview.getId());

        review.ifPresent(reviewRepository::delete);

        assertThat(reviewRepository.findById(savedReview.getId())).isEmpty();
    }

    @Test
    void findById() {
        var createdReview = createReview();
        var savedReview = reviewRepository.save(createdReview);

        var review = reviewRepository.findById(savedReview.getId());

        assertThat(review).isPresent();
        assertThat(review.get().getId()).isEqualTo(savedReview.getId());
    }

    public Review createReview() {
        return Review.builder()
                .createdAt(Instant.now())
                .evaluation(5)
                .text("Good film")
                .build();
    }
}