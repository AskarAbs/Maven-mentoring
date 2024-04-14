package com.askar.videolibrary.dto;

import com.askar.videolibrary.entity.enums.Genre;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class FilmCreateEditDto {

    String name;
    LocalDate releaseDate;
    String country;
    Genre genre;
    String trailer;
    String description;
    Long directorId;
    MultipartFile image;
}
