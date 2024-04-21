package com.askar.videolibrary.mapper;

import com.askar.videolibrary.dto.actor.ActorReadDto;
import com.askar.videolibrary.dto.film.FilmReadDto;
import com.askar.videolibrary.dto.review.ReviewReadDto;
import com.askar.videolibrary.entity.Film;
import com.askar.videolibrary.entity.FilmActor;
import com.askar.videolibrary.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilmReadMapper implements Mapper<Film, FilmReadDto> {

    private final DirectorReadMapper directorReadMapper;
    private final ActorReadMapper actorReadMapper;
    private final ReviewReadMapper reviewReadMapper;
    private final ReviewRepository reviewRepository;

    @Override
    public FilmReadDto map(Film object) {
        var director = Optional.ofNullable(object.getDirector())
                .map(directorReadMapper::map)
                .orElse(null);

        List<ReviewReadDto> reviewsDto = Optional.of(object.getReviews().stream()
                        .map(reviewReadMapper::map)
                        .toList())
                .orElse(null);

        var filmActors = object.getFilmActors();
        List<ActorReadDto> actors = Optional.of(filmActors.stream()
                        .map(FilmActor::getActor)
                        .map(actorReadMapper::map)
                        .toList())
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
                object.getImage(),
                actors,
                reviewsDto,
                reviewRepository.findAvgEvaluation(object.getId())
                );
    }
}
