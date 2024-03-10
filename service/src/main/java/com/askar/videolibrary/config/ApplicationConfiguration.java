package com.askar.videolibrary.config;

import com.askar.videolibrary.util.HibernateUtil;
import com.askar.videolibrary.util.ProxySession;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.askar.videolibrary")
public class ApplicationConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtil.buildSessionFactory();
    }

    @Bean
    public EntityManager entityManager(SessionFactory sessionFactory) {
        return ProxySession.getProxySession(sessionFactory);
    }
}
