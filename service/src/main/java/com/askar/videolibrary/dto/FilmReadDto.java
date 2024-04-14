package com.askar.videolibrary.dto;

import com.askar.videolibrary.entity.enums.Genre;
import lombok.Value;

import java.time.LocalDate;

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
}
