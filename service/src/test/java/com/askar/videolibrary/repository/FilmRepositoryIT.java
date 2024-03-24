package com.askar.videolibrary.repository;

import com.askar.videolibrary.dto.FilmFilter;
import com.askar.videolibrary.entity.enums.Genre;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class FilmRepositoryIT extends IntegrationTestBase {

    private final FilmRepository filmRepository;

    @Test
    void findAllWithFilterWhereNameIsNotNull() {
        var films = filmRepository.findFilmByFilmFilter(FilmFilter.builder()
                .name("Iron Man")
                .build());

        assertThat(films).isNotNull();
        assertThat(films).hasSize(1);
    }

    @Test
    void findAllWithFilterWhereGenreIsNotNull() {
        var films = filmRepository.findFilmByFilmFilter(FilmFilter.builder()
                .genre(Genre.ADVENTURES)
                .build());

        assertThat(films).isNotNull();
        assertThat(films).hasSize(1);
    }

    @Test
    void findAllWithFilterWhereCountryIsNotNull() {
        var films = filmRepository.findFilmByFilmFilter(FilmFilter.builder()
                .country("USA")
                .build());

        assertThat(films).isNotNull();
        assertThat(films).hasSize(2);
    }
}