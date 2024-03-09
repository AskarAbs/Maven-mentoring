package com.askar.videolibrary.repository;

import com.askar.videolibrary.util.HibernateTestUtil;
import com.askar.videolibrary.util.TestDataImporter;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class IntegrationTestBase {

    private static SessionFactory sessionFactory;
    protected EntityManager entityManager;
    protected ActorRepository actorRepository;
    protected DirectorRepository directorRepository;
    protected FilmActorRepository filmActorRepository;
    protected FilmRepository filmRepository;
    protected ReviewRepository reviewRepository;
    protected UsersRepository userRepository;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        TestDataImporter.importData(sessionFactory);
    }

    @BeforeEach
    void openSession() {
        this.entityManager = ProxySession.getProxySession(sessionFactory);
        actorRepository = new ActorRepository(entityManager);
        directorRepository = new DirectorRepository(entityManager);
        filmActorRepository = new FilmActorRepository(entityManager);
        filmRepository = new FilmRepository(entityManager);
        reviewRepository = new ReviewRepository(entityManager);
        userRepository = new UsersRepository(entityManager);
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void closeSession() {
        entityManager.getTransaction().rollback();
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }

}
