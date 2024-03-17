package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Director;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class DirectorRepositoryIT extends IntegrationTestBase {

    private final DirectorRepository directorRepository;

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
        var createdDirector = createDirector();
        var savedDirector = directorRepository.save(createdDirector);

        var director = directorRepository.findById(savedDirector.getId());
        assertThat(director).isPresent();
        director.ifPresent(director1 -> director1.setFullName("ivan"));

        director.ifPresent(directorRepository::update);
        entityManager.flush();
        var actualDirector = directorRepository.findById(director.get().getId());

        assertThat(actualDirector).isPresent();
        assertThat(actualDirector.get().getFullName()).isEqualTo("ivan");
        assertThat(actualDirector.get().getId()).isEqualTo(savedDirector.getId());
    }

    @Test
    void delete() {
        var createdDirector = createDirector();
        var savedDirector = directorRepository.save(createdDirector);
        var director = directorRepository.findById(savedDirector.getId());

        director.ifPresent(directorRepository::delete);

        assertThat(directorRepository.findById(savedDirector.getId())).isEmpty();
    }

    @Test
    void findById() {
        var createdDirector = createDirector();
        var savedDirector = directorRepository.save(createdDirector);
        var director = directorRepository.findById(savedDirector.getId());

        assertThat(director).isPresent();
        assertThat(director.get().getId()).isEqualTo(savedDirector.getId());
    }

    public Director createDirector() {
        return Director.builder()
                .birthday(LocalDate.of(2022, 12, 12))
                .fullName("Askar")
                .build();
    }
}