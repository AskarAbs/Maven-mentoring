package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Actor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class ActorRepositoryIT extends IntegrationTestBase {

    private final ActorRepository actorRepository;

    @Test
    void findAll() {
        var actors = actorRepository.findAll();
        assertThat(actors).hasSize(4);
    }

    @Test
    void save() {
        var actor = createActor();

        var actualActor = actorRepository.save(actor);

        assertThat(actualActor.getId()).isNotNull();
    }


    @Test
    void update() {
        var createdActor = createActor();
        var savedActor = actorRepository.save(createdActor);

        var actor = actorRepository.findById(savedActor.getId());
        assertThat(actor).isPresent();
        actor.ifPresent(actor1 -> actor1.setFullName("ivan"));

        actor.ifPresent(actorRepository::update);
        entityManager.flush();
        var actualActor = actorRepository.findById(actor.get().getId());

        assertThat(actualActor).isPresent();
        assertThat(actualActor.get().getFullName()).isEqualTo("ivan");
        assertThat(actualActor.get().getId()).isEqualTo(savedActor.getId());
    }

    @Test
    void delete() {
        var actor = actorRepository.findById(1L);

        actor.ifPresent(actorRepository::delete);

        assertThat(actorRepository.findById(1L)).isEmpty();
    }

    @Test
    void findById() {
        var createdActor = createActor();
        var savedActor = actorRepository.save(createdActor);

        var actor = actorRepository.findById(savedActor.getId());

        assertThat(actor).isPresent();
        assertThat(actor.get().getId()).isEqualTo(savedActor.getId());
    }

    public Actor createActor() {
        return Actor.builder()
                .fullName("Askar")
                .birthday(LocalDate.of(2001, 1, 30))
                .build();
    }


}