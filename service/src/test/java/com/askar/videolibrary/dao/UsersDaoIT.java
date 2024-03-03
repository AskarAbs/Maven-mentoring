package com.askar.videolibrary.dao;

import com.askar.videolibrary.entity.Users;
import com.askar.videolibrary.entity.enums.Role;
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

class UsersDaoIT {

    private static SessionFactory sessionFactory;
    private Session session;
    private static UsersDao userDao;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        TestDataImporter.importData(sessionFactory);
    }

    @BeforeEach
    void openSession() {
        session = getSession();
        userDao = new UsersDao(session);
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
        var users = userDao.findAll();

        assertThat(users).hasSize(2);
    }

    @Test
    void save() {
        var user = createUser();

        var actualUser = userDao.save(user);

        assertThat(actualUser.getId()).isNotNull();
    }


    @Test
    void update() {
        var user = userDao.findById(1L);
        assertThat(user).isPresent();
        user.ifPresent(users -> users.setUsername("Monkey"));

        user.ifPresent(users -> userDao.update(users));
        session.flush();
        var actualUser = userDao.findById(user.get().getId());

        assertThat(actualUser).isPresent();
        assertThat(actualUser.get().getUsername()).isEqualTo("Monkey");
        assertThat(actualUser.get().getId()).isEqualTo(1L);
    }

    @Test
    void delete() {
        var user = userDao.findById(1L);

        user.ifPresent(value -> userDao.delete(value));

        assertThat(userDao.findById(1L)).isEmpty();
    }

    @Test
    void findById() {
        var user = userDao.findById(1L);

        assertThat(user).isPresent();
        assertThat(user.get().getId()).isEqualTo(1L);
    }

    public Users createUser() {
        return Users.builder()
                .username("123askar")
                .email("123askar@gmail.com")
                .password("1234")
                .role(Role.USER)
                .build();
    }
}