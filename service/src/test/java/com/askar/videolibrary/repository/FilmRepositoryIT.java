package com.askar.videolibrary.repository;

import com.askar.videolibrary.dto.FilmFilter;
import com.askar.videolibrary.entity.Director;
import com.askar.videolibrary.entity.Film;
import com.askar.videolibrary.entity.enums.Genre;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class FilmRepositoryIT extends IntegrationTestBase {

    private final FilmRepository filmRepository;

    @Test
    void findAllWithFilterWhereNameIsNotNull() {
        var films = filmRepository.findAll(entityManager, FilmFilter.builder()
                .name("Iron Man")
                .build());

        assertThat(films).isNotNull();
        assertThat(films).hasSize(1);
    }

    @Test
    void findAllWithFilterWhereGenreIsNotNull() {
        var films = filmRepository.findAll(entityManager, FilmFilter.builder()
                .genre(Genre.ADVENTURES)
                .build());

        assertThat(films).isNotNull();
        assertThat(films).hasSize(1);
    }

    @Test
    void findAllWithFilterWhereCountryIsNotNull() {
        var films = filmRepository.findAll(entityManager, FilmFilter.builder()
                .country("USA")
                .build());

        assertThat(films).isNotNull();
        assertThat(films).hasSize(2);
    }

    @Test
    void findAll() {
        var films = filmRepository.findAll();

        assertThat(films).hasSize(2);
    }

    @Test
    void save() {
        var director = createDirector();
        var film = createFilm();
        film.setDirector(director);
        entityManager.persist(director);

        var actualFilm = filmRepository.save(film);

        assertThat(actualFilm.getId()).isNotNull();
    }


    @Test
    void update() {
        var director = createDirector();
        var film2 = createFilm();
        film2.setDirector(director);
        entityManager.persist(director);
        var createdFilm = filmRepository.save(film2);

        var film = filmRepository.findById(createdFilm.getId());
        assertThat(film).isPresent();
        film.ifPresent(film1 -> film1.setName("doctor"));

        film.ifPresent(filmRepository::update);
        entityManager.flush();
        var actualFilm = filmRepository.findById(film.get().getId());

        assertThat(actualFilm).isPresent();
        assertThat(actualFilm.get().getName()).isEqualTo("doctor");
        assertThat(actualFilm.get().getId()).isEqualTo(createdFilm.getId());
    }

    @Test
    void delete() {
        var director = createDirector();
        var film2 = createFilm();
        film2.setDirector(director);
        entityManager.persist(director);
        var createdFilm = filmRepository.save(film2);

        var actualFilm = filmRepository.findById(createdFilm.getId());

        actualFilm.ifPresent(filmRepository::delete);

        assertThat(filmRepository.findById(createdFilm.getId())).isEmpty();
    }

    @Test
    void findById() {
        var director = createDirector();
        var film2 = createFilm();
        film2.setDirector(director);
        entityManager.persist(director);
        var createdFilm = filmRepository.save(film2);

        var film = filmRepository.findById(createdFilm.getId());

        assertThat(film).isPresent();
        assertThat(film.get().getId()).isEqualTo(createdFilm.getId());
    }

    public Director createDirector() {
        return Director.builder()
                .birthday(LocalDate.of(2022, 12, 12))
                .fullName("Askar")
                .build();
    }

    public Film createFilm() {
        return Film.builder()
                .genre(Genre.ADVENTURES)
                .name("Pirates")
                .description("Pirates")
                .country("USA")
                .releaseDate(LocalDate.of(2022, 12, 12))
                .trailer("Pirates")
                .build();
    }
}