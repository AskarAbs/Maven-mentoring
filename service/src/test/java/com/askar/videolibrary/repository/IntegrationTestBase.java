package com.askar.videolibrary.repository;

import com.askar.videolibrary.annotation.IT;
import com.askar.videolibrary.util.TestDataImporter;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
public abstract class IntegrationTestBase {

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");
    @Autowired
    private EntityManager entityManager;

    @DynamicPropertySource
    static void buildEntityManagerFactory(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", postgres::getUsername);
        propertyRegistry.add("spring.datasource.password", postgres::getPassword);
    }

    static {
        postgres.start();
    }

    @BeforeEach
    void openSession() {
        TestDataImporter.importData(entityManager);
    }
}
