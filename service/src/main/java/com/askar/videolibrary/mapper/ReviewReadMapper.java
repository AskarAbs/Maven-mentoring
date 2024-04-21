package com.askar.videolibrary.mapper;

import com.askar.videolibrary.dto.review.ReviewReadDto;
import com.askar.videolibrary.entity.Review;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Component
public class ReviewReadMapper implements Mapper<Review, ReviewReadDto> {
    @Override
    public ReviewReadDto map(Review object) {
        return new ReviewReadDto(
                object.getId(),
                object.getFilm(),
                object.getUser(),
                LocalDate.ofInstant(object.getCreatedAt(), ZoneId.of(String.valueOf(ZoneOffset.UTC))),
                object.getCreatedBy(),
                object.getText(),
                object.getEvaluation()
        );
    }
}
