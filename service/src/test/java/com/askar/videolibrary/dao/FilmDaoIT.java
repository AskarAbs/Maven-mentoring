package com.askar.videolibrary.dao;

import com.askar.videolibrary.dto.FilmFilter;
import com.askar.videolibrary.entity.Director;
import com.askar.videolibrary.entity.Film;
import com.askar.videolibrary.entity.enums.Genre;
import com.askar.videolibrary.util.HibernateTestUtil;
import com.askar.videolibrary.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Proxy;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

public class FilmDaoIT {

    private static SessionFactory sessionFactory;
    private Session session;
    private static FilmDao filmDao;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        TestDataImporter.importData(sessionFactory);
    }

    @NotNull
    private static Session getSession() {
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
                new Class[]{Session.class}, (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
    }

    @BeforeEach
    void openSession() {
        session = getSession();
        filmDao = new FilmDao(session);
        session.beginTransaction();
    }

    @AfterEach
    void closeSession() {
        session.getTransaction().rollback();
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }

    @Test
    void findAllWithFilterWhereNameIsNotNull() {
        var films = filmDao.findAll(session, FilmFilter.builder()
                .name("Iron Man")
                .build());

        assertThat(films).isNotNull();
        assertThat(films).hasSize(1);
    }

    @Test
    void findAllWithFilterWhereGenreIsNotNull() {
        var films = filmDao.findAll(session, FilmFilter.builder()
                .genre(Genre.ADVENTURES)
                .build());

        assertThat(films).isNotNull();
        assertThat(films).hasSize(1);
    }

    @Test
    void findAllWithFilterWhereCountryIsNotNull() {
        var films = filmDao.findAll(session, FilmFilter.builder()
                .country("USA")
                .build());

        assertThat(films).isNotNull();
        assertThat(films).hasSize(2);
    }

    @Test
    void findAll() {
        var films = filmDao.findAll();

        assertThat(films).hasSize(2);
    }

    @Test
    void save() {
        var director = createDirector();
        var film = createFilm();
        film.setDirector(director);
        session.persist(director);

        var actualFilm = filmDao.save(film);

        assertThat(actualFilm.getId()).isNotNull();
    }


    @Test
    void update() {
        var film = filmDao.findById(1L);
        assertThat(film).isPresent();
        film.ifPresent(film1 -> film1.setName("doctor"));

        film.ifPresent(film1 -> filmDao.update(film1));
        session.flush();
        var actualFilm = filmDao.findById(film.get().getId());

        assertThat(actualFilm).isPresent();
        assertThat(actualFilm.get().getName()).isEqualTo("doctor");
        assertThat(actualFilm.get().getId()).isEqualTo(1L);
    }

    @Test
    void delete() {
        var film = filmDao.findById(1L);

        film.ifPresent(value -> filmDao.delete(value));

        assertThat(filmDao.findById(1L)).isEmpty();
    }

    @Test
    void findById() {
        var film = filmDao.findById(1L);

        assertThat(film).isPresent();
        assertThat(film.get().getId()).isEqualTo(1L);
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