package com.askar.videolibrary.dto.film;

import com.askar.videolibrary.dto.actor.ActorReadDto;
import com.askar.videolibrary.dto.director.DirectorReadDto;
import com.askar.videolibrary.dto.review.ReviewReadDto;
import com.askar.videolibrary.entity.enums.Genre;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class FilmReadDto {

    Long id;
    String name;
    LocalDate releaseDate;
    String country;
    Genre genre;
    String trailer;
    String description;
    DirectorReadDto director;
    String image;
    List<ActorReadDto> actors;
    List<ReviewReadDto> reviews;
    Integer avgEvaluation;
}
