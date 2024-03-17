package com.askar.videolibrary.util;

import com.askar.videolibrary.entity.Actor;
import com.askar.videolibrary.entity.Director;
import com.askar.videolibrary.entity.Film;
import com.askar.videolibrary.entity.FilmActor;
import com.askar.videolibrary.entity.Review;
import com.askar.videolibrary.entity.Users;
import com.askar.videolibrary.entity.enums.ActorRole;
import com.askar.videolibrary.entity.enums.Genre;
import com.askar.videolibrary.entity.enums.Role;
import jakarta.persistence.EntityManager;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDate;

@UtilityClass
public class TestDataImporter {
    public void importData(EntityManager entityManager) {

        Director jonathanFavreau = saveDirector(entityManager,
                "Jonathan Favreau",
                LocalDate.of(1966, 10, 19));
        Director guyRitchie = saveDirector(entityManager,
                "Guy Ritchie",
                LocalDate.of(1968, 9, 10));

        Actor robertDowneyJr = saveActor(entityManager,
                "Robert John Downey Jr",
                LocalDate.of(1965, 4, 4));
        Actor terrenceHoward = saveActor(entityManager,
                "Terrence Dashon Howard",
                LocalDate.of(1969, 3, 11));
        Actor jasonStatham = saveActor(entityManager,
                "Jason Statham",
                LocalDate.of(1967, 6, 26));
        Actor scottReeves = saveActor(entityManager,
                "Scott Clinton Reeves",
                LocalDate.of(1986, 3, 21));

        Film ironMan = saveFilm(entityManager,
                "Iron Man",
                LocalDate.of(2008, 3, 14),
                "USA",
                Genre.ADVENTURES,
                "https://youtu.be/fMKZMI8ByTs",
                "Миллиардер-изобретатель Тони Старк попадает",
                jonathanFavreau);
        Film wrathOfMan = saveFilm(entityManager,
                "Wrath of Man",
                LocalDate.of(2021, 3, 22),
                "USA",
                Genre.DETECTIVE,
                "https://youtu.be/YLw55x-zOSo",
                "Грузовики лос-анджелесской инкассаторской компании Fortico ",
                guyRitchie);

        Users askar = saveUser(entityManager,
                "askar@gmail.com",
                "123",
                "askar",
                Role.ADMIN);
        Users ivan = saveUser(entityManager,
                "ivan@gmail.com",
                "123",
                "ivan",
                Role.USER);

        Review askarReview = saveReview(entityManager,
                ironMan,
                askar,
                Instant.now(),
                "Good Film",
                5);
        Review ivanReview = saveReview(entityManager,
                wrathOfMan,
                ivan,
                Instant.now(),
                "Bad Film",
                1);

        FilmActor ironManRobertDowneyJr = saveFilmActor(
                entityManager,
                robertDowneyJr,
                ironMan,
                ActorRole.MAIN,
                20000L);
        FilmActor ironManTerrenceHoward = saveFilmActor(
                entityManager,
                terrenceHoward,
                ironMan,
                ActorRole.SECONDARY,
                10000L);
        FilmActor wrathOfManJasonStatham = saveFilmActor(
                entityManager,
                jasonStatham,
                wrathOfMan,
                ActorRole.MAIN,
                20000L);
        FilmActor wrathOfManScottReeves = saveFilmActor(
                entityManager,
                scottReeves,
                wrathOfMan,
                ActorRole.SECONDARY,
                1000L);

    }


    private Review saveReview(EntityManager entityManager,
                              Film film,
                              Users user,
                              Instant createdAt,
                              String text,
                              Integer evaluation) {
        var review = Review.builder()
                .film(film)
                .user(user)
                .createdAt(createdAt)
                .text(text)
                .evaluation(evaluation)
                .build();
        entityManager.persist(review);
        return review;
    }

    private FilmActor saveFilmActor(EntityManager entityManager,
                                    Actor actor,
                                    Film film,
                                    ActorRole actorRole,
                                    Long fee) {
        var filmActor = FilmActor.builder()
                .actor(actor)
                .film(film)
                .actorRole(actorRole)
                .fee(fee)
                .build();
        entityManager.persist(filmActor);
        return filmActor;
    }

    private Film saveFilm(EntityManager entityManager,
                          String name,
                          LocalDate releaseDate,
                          String country,
                          Genre genre,
                          String trailer,
                          String description,
                          Director director) {
        var film = Film.builder()
                .name(name)
                .releaseDate(releaseDate)
                .country(country)
                .genre(genre)
                .trailer(trailer)
                .description(description)
                .director(director)
                .build();
        entityManager.persist(film);

        return film;
    }

    private Director saveDirector(EntityManager entityManager,
                                  String fullName,
                                  LocalDate birthday) {
        var director = Director.builder()
                .fullName(fullName)
                .birthday(birthday)
                .build();
        entityManager.persist(director);
        return director;
    }

    private Actor saveActor(EntityManager entityManager,
                            String fullName,
                            LocalDate birthday) {
        var actor = Actor.builder()
                .fullName(fullName)
                .birthday(birthday)
                .build();
        entityManager.persist(actor);
        return actor;
    }

    private Users saveUser(EntityManager entityManager,
                           String email,
                           String password,
                           String username,
                           Role role) {
        Users user = Users.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .build();
        entityManager.persist(user);
        return user;
    }
}
