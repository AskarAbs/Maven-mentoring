package com.askar.videolibrary.repository;

import com.askar.videolibrary.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@IT
@RequiredArgsConstructor
public class ReviewRepositoryIT extends IntegrationTestBase {

    private final FilmRepository filmRepository;
    private final ReviewRepository reviewRepository;

    @Test
    void findAvgEvaluation() {
        var film = filmRepository.findById(1L);
        film.ifPresent(film1 -> {
            var avgEvaluation = reviewRepository.findAvgEvaluation(film1.getId());
            Assertions.assertThat(avgEvaluation).isNotNull();
            Assertions.assertThat(avgEvaluation).isGreaterThan(0);
        });
    }
}
