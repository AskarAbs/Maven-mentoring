package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.FilmActor;
import com.askar.videolibrary.entity.enums.ActorRole;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class FilmActorRepositoryIT extends IntegrationTestBase {

    private final FilmActorRepository filmActorRepository;

    @Test
    void findAll() {
        var filmActors = filmActorRepository.findAll();

        assertThat(filmActors).hasSize(4);
    }

    @Test
    void save() {
        var filmActor = createfilmActor();

        var actualFilmActor = filmActorRepository.save(filmActor);

        assertThat(actualFilmActor.getId()).isNotNull();
    }


    @Test
    void update() {
        var createdFilmActor = createfilmActor();
        var savedFilmActor = filmActorRepository.save(createdFilmActor);

        var filmActor = filmActorRepository.findById(savedFilmActor.getId());
        assertThat(filmActor).isPresent();
        filmActor.ifPresent(filmActors -> filmActors.setFee(400L));

        filmActor.ifPresent(filmActorRepository::update);
        entityManager.flush();
        var actualFilmActors = filmActorRepository.findById(filmActor.get().getId());

        assertThat(actualFilmActors).isPresent();
        assertThat(actualFilmActors.get().getFee()).isEqualTo(400L);
        assertThat(actualFilmActors.get().getId()).isEqualTo(savedFilmActor.getId());
    }

    @Test
    void delete() {
        var createdFilmActor = createfilmActor();
        var savedFilmActor = filmActorRepository.save(createdFilmActor);

        var filmActor = filmActorRepository.findById(savedFilmActor.getId());

        filmActor.ifPresent(filmActorRepository::delete);

        assertThat(filmActorRepository.findById(savedFilmActor.getId())).isEmpty();
    }

    @Test
    void findById() {
        var createdFilmActor = createfilmActor();
        var savedFilmActor = filmActorRepository.save(createdFilmActor);

        var filmActor = filmActorRepository.findById(savedFilmActor.getId());

        assertThat(filmActor).isPresent();
        assertThat(filmActor.get().getId()).isEqualTo(savedFilmActor.getId());
    }

    public FilmActor createfilmActor() {
        return FilmActor.builder()
                .fee(2300L)
                .actorRole(ActorRole.MAIN)
                .build();
    }
}