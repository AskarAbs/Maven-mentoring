package com.askar.videolibrary.dto;

import com.askar.videolibrary.entity.Director;
import com.askar.videolibrary.entity.Review;
import com.askar.videolibrary.entity.enums.Genre;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class FilmFilter {

    Genre genre;
    String name;
    String country;
}
