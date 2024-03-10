package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Director;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DirectorRepositoryIT extends IntegrationTestBase {

    private static final Long DIRECTOR_ID = 1L;
    private static DirectorRepository directorRepository;

    @BeforeAll
    static void initActor() {
        directorRepository = context.getBean("directorRepository", DirectorRepository.class);
    }


    @Test
    void findAll() {
        var actualDirectors = directorRepository.findAll();

        assertThat(actualDirectors).hasSize(2);
    }

    @Test
    void save() {
        var director = createDirector();

        var actualDirector = directorRepository.save(director);

        assertThat(actualDirector.getId()).isNotNull();
    }


    @Test
    void update() {
        var director = directorRepository.findById(DIRECTOR_ID);
        assertThat(director).isPresent();
        director.ifPresent(director1 -> director1.setFullName("ivan"));

        director.ifPresent(director1 -> directorRepository.update(director1));
        entityManager.flush();
        var actualDirector = directorRepository.findById(director.get().getId());

        assertThat(actualDirector).isPresent();
        assertThat(actualDirector.get().getFullName()).isEqualTo("ivan");
        assertThat(actualDirector.get().getId()).isEqualTo(DIRECTOR_ID);
    }

    @Test
    void delete() {
        var director = directorRepository.findById(DIRECTOR_ID);

        director.ifPresent(value -> directorRepository.delete(value));

        assertThat(directorRepository.findById(DIRECTOR_ID)).isEmpty();
    }

    @Test
    void findById() {
        var director = directorRepository.findById(DIRECTOR_ID);

        assertThat(director).isPresent();
        assertThat(director.get().getId()).isEqualTo(DIRECTOR_ID);
    }

    public Director createDirector() {
        return Director.builder()
                .birthday(LocalDate.of(2022, 12, 12))
                .fullName("Askar")
                .build();
    }
}