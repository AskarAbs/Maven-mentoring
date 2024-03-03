package com.askar.videolibrary.dao;

import com.askar.videolibrary.entity.Actor;
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

class ActorDaoIT {

    private static SessionFactory sessionFactory;
    private Session session;
    private static ActorDao actorDao;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        TestDataImporter.importData(sessionFactory);
    }

    @BeforeEach
    void openSession() {
        session = getSession();
        actorDao = new ActorDao(session);
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
        var actors = actorDao.findAll();

        assertThat(actors).hasSize(4);
    }

    @Test
    void save() {
        var actor = createActor();

        var actualActor = actorDao.save(actor);

        assertThat(actualActor.getId()).isNotNull();
    }


    @Test
    void update() {
        var actor = actorDao.findById(1L);
        assertThat(actor).isPresent();
        actor.ifPresent(actor1 -> actor1.setFullName("ivan"));

        actor.ifPresent(actor1 -> actorDao.update(actor1));
        session.flush();
        var actualActor = actorDao.findById(actor.get().getId());

        assertThat(actualActor).isPresent();
        assertThat(actualActor.get().getFullName()).isEqualTo("ivan");
        assertThat(actualActor.get().getId()).isEqualTo(1L);
    }

    @Test
    void delete() {
        var actor = actorDao.findById(1L);

        actor.ifPresent(value -> actorDao.delete(value));

        assertThat(actorDao.findById(1L)).isEmpty();
    }

    @Test
    void findById() {
        var actor = actorDao.findById(1L);

        assertThat(actor).isPresent();
        assertThat(actor.get().getId()).isEqualTo(1L);
    }

    public Actor createActor() {
        return Actor.builder()
                .fullName("Askar")
                .birthday(LocalDate.of(2001, 1, 30))
                .build();
    }


}