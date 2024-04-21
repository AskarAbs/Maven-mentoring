package com.askar.videolibrary.dto.review;

import lombok.Value;

@Value
public class ReviewCreateEditDto {

    String email;
    Long filmId;
    String text;
    Integer evaluation;
}
