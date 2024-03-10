package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.FilmActor;
import com.askar.videolibrary.entity.enums.ActorRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FilmActorRepositoryIT extends IntegrationTestBase {

    private static final Long FILM_ACTOR_ID = 1L;
    private static FilmActorRepository filmActorRepository;

    @BeforeAll
    static void initActor() {
        filmActorRepository = context.getBean("filmActorRepository", FilmActorRepository.class);
    }

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
        var filmActor = filmActorRepository.findById(FILM_ACTOR_ID);
        assertThat(filmActor).isPresent();
        filmActor.ifPresent(filmActors -> filmActors.setFee(400L));

        filmActor.ifPresent(filmActors -> filmActorRepository.update(filmActors));
        entityManager.flush();
        var actualFilmActors = filmActorRepository.findById(filmActor.get().getId());

        assertThat(actualFilmActors).isPresent();
        assertThat(actualFilmActors.get().getFee()).isEqualTo(400L);
        assertThat(actualFilmActors.get().getId()).isEqualTo(FILM_ACTOR_ID);
    }

    @Test
    void delete() {
        var filmActor = filmActorRepository.findById(FILM_ACTOR_ID);

        filmActor.ifPresent(value -> filmActorRepository.delete(value));

        assertThat(filmActorRepository.findById(FILM_ACTOR_ID)).isEmpty();
    }

    @Test
    void findById() {
        var filmActor = filmActorRepository.findById(FILM_ACTOR_ID);

        assertThat(filmActor).isPresent();
        assertThat(filmActor.get().getId()).isEqualTo(FILM_ACTOR_ID);
    }

    public FilmActor createfilmActor() {
        return FilmActor.builder()
                .fee(2300L)
                .actorRole(ActorRole.MAIN)
                .build();
    }
}