package com.askar.videolibrary.dao;

import com.askar.videolibrary.entity.Director;
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

class DirectorDaoIT {

    private static SessionFactory sessionFactory;
    private Session session;
    private static DirectorDao directorDao;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        TestDataImporter.importData(sessionFactory);
    }

    @BeforeEach
    void openSession() {
        session = getSession();
        directorDao = new DirectorDao(session);
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
        var directors = directorDao.findAll();

        assertThat(directors).hasSize(2);
    }

    @Test
    void save() {
        var director = createDirector();

        var actualDirector = directorDao.save(director);

        assertThat(actualDirector.getId()).isNotNull();
    }


    @Test
    void update() {
        var director = directorDao.findById(1L);
        assertThat(director).isPresent();
        director.ifPresent(director1 -> director1.setFullName("ivan"));

        director.ifPresent(director1 -> directorDao.update(director1));
        session.flush();
        var actualDirector = directorDao.findById(director.get().getId());

        assertThat(actualDirector).isPresent();
        assertThat(actualDirector.get().getFullName()).isEqualTo("ivan");
        assertThat(actualDirector.get().getId()).isEqualTo(1L);
    }

    @Test
    void delete() {
        var director = directorDao.findById(1L);

        director.ifPresent(value -> directorDao.delete(value));

        assertThat(directorDao.findById(1L)).isEmpty();
    }

    @Test
    void findById() {
        var director = directorDao.findById(1L);

        assertThat(director).isPresent();
        assertThat(director.get().getId()).isEqualTo(1L);
    }

    public Director createDirector() {
        return Director.builder()
                .birthday(LocalDate.of(2022, 12, 12))
                .fullName("Askar")
                .build();
    }
}