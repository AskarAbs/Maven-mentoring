package com.askar.videolibrary.mapper;

import com.askar.videolibrary.dto.FilmReadDto;
import com.askar.videolibrary.entity.Film;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilmReadMapper implements Mapper<Film, FilmReadDto> {

    private final DirectorReadMapper directorReadMapper;

    @Override
    public FilmReadDto map(Film object) {
        var director = Optional.ofNullable(object.getDirector())
                .map(directorReadMapper::map)
                .orElse(null);
        return new FilmReadDto(
                object.getId(),
                object.getName(),
                object.getReleaseDate(),
                object.getCountry(),
                object.getGenre(),
                object.getTrailer(),
                object.getDescription(),
                director,
                object.getImage()
        );
    }
}
