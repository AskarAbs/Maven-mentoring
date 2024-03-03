package com.askar.videolibrary.dao;

import com.askar.videolibrary.entity.FilmActor;
import com.askar.videolibrary.entity.enums.ActorRole;
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

import static org.assertj.core.api.Assertions.assertThat;

class FilmActorDaoIT {

    private static SessionFactory sessionFactory;
    private Session session;
    private static FilmActorDao filmActorDao;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        TestDataImporter.importData(sessionFactory);
    }

    @BeforeEach
    void openSession() {
        session = getSession();
        filmActorDao = new FilmActorDao(session);
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

    @NotNull
    private static Session getSession() {
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
                new Class[]{Session.class}, (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
    }

    @Test
    void findAll() {
        var filmActors = filmActorDao.findAll();

        assertThat(filmActors).hasSize(4);
    }

    @Test
    void save() {
        var filmActor = createfilmActor();

        var actualFilmActor = filmActorDao.save(filmActor);

        assertThat(actualFilmActor.getId()).isNotNull();
    }


    @Test
    void update() {
        var filmActor = filmActorDao.findById(1L);
        assertThat(filmActor).isPresent();
        filmActor.ifPresent(filmActors -> filmActors.setFee(400L));

        filmActor.ifPresent(filmActors -> filmActorDao.update(filmActors));
        session.flush();
        var actualFilmActors = filmActorDao.findById(filmActor.get().getId());

        assertThat(actualFilmActors).isPresent();
        assertThat(actualFilmActors.get().getFee()).isEqualTo(400L);
        assertThat(actualFilmActors.get().getId()).isEqualTo(1L);
    }

    @Test
    void delete() {
        var filmActor = filmActorDao.findById(1L);

        filmActor.ifPresent(value -> filmActorDao.delete(value));

        assertThat(filmActorDao.findById(1L)).isEmpty();
    }

    @Test
    void findById() {
        var filmActor = filmActorDao.findById(1L);

        assertThat(filmActor).isPresent();
        assertThat(filmActor.get().getId()).isEqualTo(1L);
    }

    public FilmActor createfilmActor() {
        return FilmActor.builder()
                .fee(2300L)
                .actorRole(ActorRole.MAIN)
                .build();
    }
}