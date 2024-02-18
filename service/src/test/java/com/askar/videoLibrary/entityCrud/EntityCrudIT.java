package com.askar.videoLibrary.entityCrud;

import com.askar.videoLibrary.entity.Actor;
import com.askar.videoLibrary.entity.Director;
import com.askar.videoLibrary.entity.Film;
import com.askar.videoLibrary.entity.FilmActor;
import com.askar.videoLibrary.entity.Review;
import com.askar.videoLibrary.entity.Users;
import com.askar.videoLibrary.entity.enums.ActorRole;
import com.askar.videoLibrary.entity.enums.Genre;
import com.askar.videoLibrary.entity.enums.Role;
import com.askar.videoLibrary.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class EntityCrudIT {

    private static SessionFactory sessionFactory = null;
    private Session session;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @BeforeEach
    void openSession() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void closeSession() {
        this.session.getTransaction().rollback();
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }

    @Nested
    class NestedActorTest {
        @Test
        void checkFindByIdActor() {
            var actor = createActor();
            var actor2 = createActor();

            session.persist(actor);
            session.persist(actor2);
            var actor1 = session.get(Actor.class, actor2.getId());

            assertThat(actor1).isEqualTo(actor2);
        }

        @Test
        void checkUpdateFullNameInActor() {
            var actor = createActor();
            session.persist(actor);

            var actualActor = session.get(Actor.class, actor.getId());
            actualActor.setFullName("Vova");
            var actualResult = session.merge(actualActor);

            actor.setFullName("Vova");
            assertThat(actualResult).isEqualTo(actor);
        }

        @Test
        void checkDeleteActor() {
            var actor = createActor();
            session.persist(actor);

            var actor1 = session.get(Actor.class, actor.getId());
            session.remove(actor1);
            session.flush();
            var actualResult = session.get(Actor.class, actor.getId());

            assertThat(actualResult).isEqualTo(null);
        }

    }

    @Nested
    class NestedDirectorTest {
        @Test
        void checkFindByIdDirector() {
            var director = createDirector();
            var expectedDirector2 = createDirector();
            session.persist(director);
            session.persist(expectedDirector2);

            var actualDirector = session.get(Director.class, expectedDirector2.getId());

            assertThat(actualDirector).isEqualTo(expectedDirector2);
        }

        @Test
        void checkUpdateFullNameInDirector() {
            var expectedDirector = createDirector();
            session.persist(expectedDirector);

            var director = session.get(Director.class, expectedDirector.getId());
            director.setFullName("Vova");
            var actualDirector = session.merge(director);

            expectedDirector.setFullName("Vova");
            assertThat(actualDirector).isEqualTo(expectedDirector);
        }

        @Test
        void checkDeleteDirector() {
            var director = createDirector();
            session.persist(director);

            var director1 = session.get(Director.class, director.getId());
            session.remove(director1);
            session.flush();
            var actualResult = session.get(Director.class, director.getId());

            assertThat(actualResult).isEqualTo(null);
        }

        @Test
        void checkAddingFilmInCollectionFilms() {
            var director = createDirector();
            var film = createFilm();
            film.setDirector(director);
            session.persist(director);
            session.persist(film);

            director.addFilm(film);
            var directorFilms = director.getFilms();

            assertThat(directorFilms.contains(film)).isTrue();
        }
    }

    @Nested
    class NestedFilmTest {
        @Test
        void checkCreateAndFindByIdFilm() {
            Film film = getFilm();
            var film1 = getFilm();
            film1.setGenre(Genre.DETECTIVE);

            var actualfilm = session.get(Film.class, film1.getId());

            assertThat(actualfilm).isEqualTo(film1);
        }

        @Test
        void checkUpdateGenreInFilm() {
            Film expectedFilm = getFilm();
            var actualFilm = session.get(Film.class, expectedFilm.getId());
            expectedFilm.setGenre(Genre.COMEDY);
            actualFilm.setGenre(Genre.COMEDY);

            session.merge(actualFilm);

            assertThat(actualFilm).isEqualTo(expectedFilm);
        }

        @Test
        void checkDeleteFilm() {
            Film expectedFilm = getFilm();

            var actualFilm = session.get(Film.class, expectedFilm.getId());
            session.remove(actualFilm);
            var deletedFilm = session.get(Film.class, expectedFilm.getId());

            assertThat(deletedFilm).isEqualTo(null);
        }

        @Test
        void checkWhileDeleteDirectorFilmNotDeleted() {
            var expectedFilm = getFilm();
            var director = expectedFilm.getDirector();

            var director1 = session.get(Director.class, director.getId());
            session.remove(director1);
            var actualfilm = session.get(Film.class, expectedFilm.getId());

            expectedFilm.setDirector(null);
            assertThat(actualfilm).isEqualTo(expectedFilm);
        }

        @Test
        void checkAddingReviewInCollectionReviews() {
            var film = getFilm();
            var user = createUser();
            var review = createReview();
            review.setUser(user);
            review.setFilm(film);
            session.persist(user);
            session.persist(review);

            film.addReview(review);
            var actualFilm = session.get(Film.class, film.getId());
            var filmReviews = actualFilm.getReviews();

            assertThat(filmReviews.contains(review)).isTrue();
        }

        @NotNull
        private Film getFilm() {
            var director = createDirector();
            var film = createFilm();
            film.setDirector(director);
            session.persist(director);
            session.persist(film);
            return film;
        }
    }

    @Nested
    class NestedFilmActorTest {
        @Test
        void checkCreateAndFindByIdFilmActor() {
            FilmActor filmActor = getFilmActor();
            FilmActor filmActor1 = getFilmActor();
            filmActor1.setFee(5000L);

            var actualFilmActor = session.get(FilmActor.class, filmActor1.getId());

            assertThat(actualFilmActor).isEqualTo(filmActor1);
        }

        @Test
        void checkUpdateActorInFilmActor() {
            FilmActor filmActor = getFilmActor();

            var expectedFilmActor = session.get(FilmActor.class, filmActor.getId());
            var newActor = createActor();
            newActor.setFullName("Vova");
            session.persist(newActor);
            expectedFilmActor.setActors(newActor);
            var mergedFilmActor = session.merge(expectedFilmActor);

            assertThat(mergedFilmActor.getActor()).isEqualTo(newActor);
        }

        @Test
        void checkDeleteFilmActor() {
            var filmActor = getFilmActor();

            var filmActor1 = session.get(FilmActor.class, filmActor.getId());
            session.remove(filmActor1);
            var deletedFilmActor = session.get(FilmActor.class, filmActor.getId());

            assertThat(deletedFilmActor).isNull();
            assertThat(filmActor.getActor()).isNotNull();
            assertThat(filmActor.getFilm()).isNotNull();
        }

        @NotNull
        private FilmActor getFilmActor() {
            var director = createDirector();
            var film = createFilm();
            film.setDirector(director);
            session.persist(director);
            session.persist(film);

            var actor = createActor();
            session.persist(actor);

            var filmActor = createfilmActor();
            filmActor.setActors(actor);
            filmActor.setFilm(film);
            session.persist(filmActor);
            return filmActor;
        }
    }

    @Nested
    class NestedReviewsTest {
        @Test
        void checkCreateAndFindByIdReviews() {
            Review review = getReview();

            var actualReview = session.get(Review.class, review.getId());

            assertThat(actualReview).isEqualTo(review);
        }

        @Test
        void checkUpdateEvaluationInReview() {
            Review review = getReview();

            var beforeUpdateReview = session.get(Review.class, review.getId());
            beforeUpdateReview.setEvaluation(3);
            var mergedReview = session.merge(beforeUpdateReview);

            review.setEvaluation(3);
            assertThat(mergedReview).isEqualTo(review);
        }

        @Test
        void checkDeleteReview() {
            var review = getReview();

            var beforeDeleteReview = session.get(Review.class, review.getId());
            session.remove(beforeDeleteReview);
            var deletedReview = session.get(Review.class, review.getId());

            assertThat(deletedReview).isNull();
            assertThat(review.getUser()).isNotNull();
            assertThat(review.getFilm()).isNotNull();
        }


        @NotNull
        private Review getReview() {
            var director = createDirector();
            var film = createFilm();
            film.setDirector(director);
            session.persist(director);
            session.persist(film);

            var user = createUser();
            var review = createReview();
            review.setUser(user);
            review.setFilm(film);
            session.persist(user);
            session.persist(review);
            return review;
        }
    }

    @Nested
    class NestedUserTest {
        @Test
        void checkCreateAndFindByIdUser() {
            var user = createUser();
            var user1 = createUser();
            user1.setEmail("second@gmail.com");
            session.persist(user);
            session.persist(user1);

            var actualUser = session.get(Users.class, user1.getId());

            assertThat(actualUser).isEqualTo(user1);
        }

        @Test
        void checkUpdateUsernameInUser() {
            var user = createUser();
            session.persist(user);
            user.setUsername("Kola");

            var beforeUpdateUser = session.get(Users.class, user.getId());
            beforeUpdateUser.setUsername("Kola");
            var actualUser = session.merge(beforeUpdateUser);

            assertThat(actualUser).isEqualTo(user);
        }

        @Test
        void checkDeleteUser() {
            var user = createUser();
            session.persist(user);

            var beforeDeleteUser = session.get(Users.class, user.getId());
            session.remove(beforeDeleteUser);
            var actualResult = session.get(Users.class, user.getId());

            assertThat(actualResult).isNull();
        }
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

    public Actor createActor() {
        return Actor.builder()
                .fullName("Askar")
                .birthday(LocalDate.of(2001, 1, 30))
                .build();
    }

    public FilmActor createfilmActor() {
        return FilmActor.builder()
                .fee(2300L)
                .actorRole(ActorRole.MAIN)
                .build();
    }

    public Review createReview() {
        return Review.builder()
                .createdAt(Instant.now())
                .evaluation(5)
                .text("Good film")
                .build();
    }

    public Users createUser() {
        return Users.builder()
                .username("askar")
                .email("askar@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();
    }
}