package com.askar.videolibrary.repository;

import com.askar.videolibrary.annotation.IT;
import com.askar.videolibrary.entity.enums.Role;
import com.askar.videolibrary.util.TestDataImporter;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Arrays;
import java.util.List;

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

        List<GrantedAuthority> roles = Arrays.asList(Role.ADMIN, Role.USER);
        var testUser = new User("test@gmail.com", "test", roles);
        var testingAuthenticationToken = new TestingAuthenticationToken(testUser, testUser.getPassword(), roles);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(testingAuthenticationToken);
        SecurityContextHolder.setContext(securityContext);
    }
}
