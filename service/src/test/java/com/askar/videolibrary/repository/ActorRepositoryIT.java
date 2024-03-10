package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Actor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ActorRepositoryIT extends IntegrationTestBase {

    private static final Long ACTOR_ID = 1L;
    private static ActorRepository actorRepository;

    @BeforeAll
    static void initActor() {
        actorRepository = context.getBean("actorRepository", ActorRepository.class);
    }

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
        var actor = actorRepository.findById(ACTOR_ID);
        assertThat(actor).isPresent();
        actor.ifPresent(actor1 -> actor1.setFullName("ivan"));

        actor.ifPresent(actorRepository::update);
        entityManager.flush();
        var actualActor = actorRepository.findById(actor.get().getId());

        assertThat(actualActor).isPresent();
        assertThat(actualActor.get().getFullName()).isEqualTo("ivan");
        assertThat(actualActor.get().getId()).isEqualTo(ACTOR_ID);
    }

    @Test
    void delete() {
        var actor = actorRepository.findById(1L);

        actor.ifPresent(actorRepository::delete);

        assertThat(actorRepository.findById(1L)).isEmpty();
    }

    @Test
    void findById() {
        var actor = actorRepository.findById(ACTOR_ID);

        assertThat(actor).isPresent();
        assertThat(actor.get().getId()).isEqualTo(ACTOR_ID);
    }

    public Actor createActor() {
        return Actor.builder()
                .fullName("Askar")
                .birthday(LocalDate.of(2001, 1, 30))
                .build();
    }


}