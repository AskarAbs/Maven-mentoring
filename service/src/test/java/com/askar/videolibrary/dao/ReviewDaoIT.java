package com.askar.videolibrary.dao;

import com.askar.videolibrary.entity.Review;
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
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewDaoIT {

    private static SessionFactory sessionFactory;
    private Session session;
    private static ReviewDao reviewDao;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        TestDataImporter.importData(sessionFactory);
    }

    @BeforeEach
    void openSession() {
        session = getSession();
        reviewDao = new ReviewDao(session);
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
        var reviews = reviewDao.findAll();

        assertThat(reviews).hasSize(2);
    }

    @Test
    void save() {
        var review = createReview();

        var actualReview = reviewDao.save(review);

        assertThat(actualReview.getId()).isNotNull();
    }


    @Test
    void update() {
        var review = reviewDao.findById(1L);
        assertThat(review).isPresent();
        review.ifPresent(reviews -> reviews.setEvaluation(1));

        review.ifPresent(reviews -> reviewDao.update(reviews));
        session.flush();
        var actualReview = reviewDao.findById(review.get().getId());

        assertThat(actualReview).isPresent();
        assertThat(actualReview.get().getEvaluation()).isEqualTo(1);
        assertThat(actualReview.get().getId()).isEqualTo(1L);
    }

    @Test
    void delete() {
        var review = reviewDao.findById(1L);

        review.ifPresent(value -> reviewDao.delete(value));

        assertThat(reviewDao.findById(1L)).isEmpty();
    }

    @Test
    void findById() {
        var review = reviewDao.findById(1L);

        assertThat(review).isPresent();
        assertThat(review.get().getId()).isEqualTo(1L);
    }

    public Review createReview() {
        return Review.builder()
                .createdAt(Instant.now())
                .evaluation(5)
                .text("Good film")
                .build();
    }
}