package com.askar.videolibrary.repository;

import com.askar.videolibrary.config.TestApplicationConfiguration;
import com.askar.videolibrary.util.TestDataImporter;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class IntegrationTestBase {

    protected static AnnotationConfigApplicationContext context;
    private static SessionFactory sessionFactory;
    protected EntityManager entityManager;

    @BeforeAll
    static void initSessionFactory() {
        context = new AnnotationConfigApplicationContext(TestApplicationConfiguration.class);
        sessionFactory = context.getBean("sessionFactory", SessionFactory.class);
        TestDataImporter.importData(sessionFactory);
    }

    @BeforeEach
    void openSession() {
        entityManager = context.getBean("entityManager", EntityManager.class);
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void closeSession() {
        entityManager.getTransaction().rollback();
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
        context.close();
    }

}
