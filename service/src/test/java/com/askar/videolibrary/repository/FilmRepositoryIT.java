package com.askar.videolibrary.repository;

import com.askar.videolibrary.dto.FilmFilter;
import com.askar.videolibrary.entity.Director;
import com.askar.videolibrary.entity.Film;
import com.askar.videolibrary.entity.enums.Genre;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class FilmRepositoryIT extends IntegrationTestBase {

    private static final Long FILM_ID = 1L;
    private static FilmRepository filmRepository;

    @BeforeAll
    static void initActor() {
        filmRepository = context.getBean("filmRepository", FilmRepository.class);
    }

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
        var film = filmRepository.findById(FILM_ID);
        assertThat(film).isPresent();
        film.ifPresent(film1 -> film1.setName("doctor"));

        film.ifPresent(film1 -> filmRepository.update(film1));
        entityManager.flush();
        var actualFilm = filmRepository.findById(film.get().getId());

        assertThat(actualFilm).isPresent();
        assertThat(actualFilm.get().getName()).isEqualTo("doctor");
        assertThat(actualFilm.get().getId()).isEqualTo(FILM_ID);
    }

    @Test
    void delete() {
        var film = filmRepository.findById(FILM_ID);

        film.ifPresent(value -> filmRepository.delete(value));

        assertThat(filmRepository.findById(1L)).isEmpty();
    }

    @Test
    void findById() {
        var film = filmRepository.findById(FILM_ID);

        assertThat(film).isPresent();
        assertThat(film.get().getId()).isEqualTo(FILM_ID);
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