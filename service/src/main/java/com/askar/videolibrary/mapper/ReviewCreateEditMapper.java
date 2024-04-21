package com.askar.videolibrary.mapper;

import com.askar.videolibrary.config.AuditConfiguration;
import com.askar.videolibrary.dto.review.ReviewCreateEditDto;
import com.askar.videolibrary.entity.Film;
import com.askar.videolibrary.entity.Review;
import com.askar.videolibrary.entity.Users;
import com.askar.videolibrary.repository.FilmRepository;
import com.askar.videolibrary.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewCreateEditMapper implements Mapper<ReviewCreateEditDto, Review> {

    private final AuditConfiguration auditConfiguration;
    private final FilmRepository filmRepository;
    private final UsersRepository usersRepository;

    @Override
    public Review map(ReviewCreateEditDto object) {
        var review = new Review();
        copy(review, object);
        return review;
    }

    @Override
    public Review map(ReviewCreateEditDto fromObject, Review toObject) {
        copy(toObject, fromObject);
        return toObject;
    }

    private void copy(Review review, ReviewCreateEditDto object) {
        review.setEvaluation(object.getEvaluation());
        review.setCreatedAt(Instant.now());
        review.setCreatedBy(String.valueOf(auditConfiguration.auditorAware().getCurrentAuditor()));
        review.setUser(getUser(object.getEmail()).orElseThrow());
        review.setText(object.getText());
        review.setFilm(getFilm(object.getFilmId()).orElseThrow());
    }

    private Optional<Film> getFilm(Long filmId) {
        return Optional.ofNullable(filmId)
                .map(filmRepository::findById)
                .orElse(null);
    }

    private Optional<Users> getUser(String email) {
        return Optional.ofNullable(email)
                .map(usersRepository::findByEmail)
                .orElse(null);
    }
}
