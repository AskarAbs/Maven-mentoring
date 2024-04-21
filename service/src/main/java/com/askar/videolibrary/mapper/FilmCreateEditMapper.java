package com.askar.videolibrary.mapper;

import com.askar.videolibrary.dto.film.FilmCreateEditDto;
import com.askar.videolibrary.entity.Director;
import com.askar.videolibrary.entity.Film;
import com.askar.videolibrary.entity.FilmActor;
import com.askar.videolibrary.repository.ActorRepository;
import com.askar.videolibrary.repository.DirectorRepository;
import com.askar.videolibrary.repository.FilmActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class FilmCreateEditMapper implements Mapper<FilmCreateEditDto, Film> {

    private final DirectorRepository directorRepository;
    private final FilmActorRepository filmActorRepository;

    @Override
    public Film map(FilmCreateEditDto object) {
        var film = new Film();
        copy(film, object);
        return film;
    }

    @Override
    public Film map(FilmCreateEditDto fromObject, Film toObject) {
        copy(toObject, fromObject);
        return toObject;
    }

    private Director getDirector(Long directorId) {
        return Optional.ofNullable(directorId)
                .flatMap(directorRepository::findById)
                .orElse(null);
    }

    private void copy(Film film, FilmCreateEditDto object) {
        film.setName(object.getName());
        film.setDirector(getDirector(object.getDirectorId()));
        film.setReleaseDate(object.getReleaseDate());
        film.setCountry(object.getCountry());
        film.setGenre(object.getGenre());
        film.setTrailer(object.getTrailer());
        film.setDescription(object.getDescription());
        film.setFilmActors(getActors(object.getFilmActorId()).stream().toList());

        Optional.ofNullable(object.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> film.setImage(image.getOriginalFilename()));
    }

    private Optional<FilmActor> getActors(Long filmActorId){
        return Optional.ofNullable(filmActorId)
                .map(filmActorRepository::findById)
                .orElse(null);
    }
}
