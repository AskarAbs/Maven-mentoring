package com.askar.videolibrary.dto.review;

import com.askar.videolibrary.entity.Film;
import com.askar.videolibrary.entity.Users;
import lombok.Value;

import java.time.LocalDate;

@Value
public class ReviewReadDto {

    Long id;
    Film film;
    Users user;
    LocalDate createdAt;
    String createdBy;
    String text;
    Integer evaluation;
}
