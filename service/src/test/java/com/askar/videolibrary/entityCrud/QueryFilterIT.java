package com.askar.videolibrary.entityCrud;

import com.askar.videolibrary.dao.QPredicate;
import com.askar.videolibrary.dto.FilmFilter;
import com.askar.videolibrary.entity.Director;
import com.askar.videolibrary.entity.Film;
import com.askar.videolibrary.entity.Film_;
import com.askar.videolibrary.entity.enums.Genre;
import com.askar.videolibrary.util.HibernateTestUtil;
import com.askar.videolibrary.util.TestDataImporter;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static com.askar.videolibrary.entity.QFilm.film;
import static org.assertj.core.api.Assertions.assertThat;


public class QueryFilterIT {

    private static SessionFactory sessionFactory;
    private static Session session;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        TestDataImporter.importData(sessionFactory);
    }

    @BeforeEach
    void openSession() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void closeSession() {
        session.getTransaction().rollback();
    }

    @AfterAll
    static void closeSessionFactory() {
        session.close();
        sessionFactory.close();
    }

    @Test
    void checkFindDirectorInFilmWhereGenreIsNullCriteria() {
        var directors1 = findDirectorInFilmCriteria(session, Genre.ADVENTURES, null, null);
        assertThat(directors1).hasSize(1);
    }

    @Test
    void checkFindDirectorInFilmWhereNameIsNullCriteria() {
        var directors1 = findDirectorInFilmCriteria(session, null, "Iron Man", null);
        assertThat(directors1).hasSize(1);
    }

    @Test
    void checkFindDirectorInFilmWhereCountryIsNullCriteria() {
        var directors1 = findDirectorInFilmCriteria(session, null, null, "USA");
        assertThat(directors1).hasSize(2);
    }

    @Test
    void checkFindDirectorInFilmWhereGenreIsNullQueryDsl() {
        var directors2 = findDirectorInFilmQuerydsl(session, FilmFilter.builder()
                .genre(Genre.ADVENTURES)
                .country(null)
                .name(null)
                .build());
        assertThat(directors2).hasSize(1);
    }

    @Test
    void checkFindDirectorInFilmWhereNameIsNullQueryDsl() {
        var directors2 = findDirectorInFilmQuerydsl(session, FilmFilter.builder()
                .genre(null)
                .country(null)
                .name("Iron Man")
                .build());
        assertThat(directors2).hasSize(1);
    }

    @Test
    void checkFindDirectorInFilmWhereCountryIsNullQueryDsl() {
        var directors2 = findDirectorInFilmQuerydsl(session, FilmFilter.builder()
                .genre(null)
                .country("USA")
                .name(null)
                .build());
        assertThat(directors2).hasSize(2);
    }

    public List<Director> findDirectorInFilmCriteria(Session session, Genre genre, String name, String country) {
        var filmGraph = session.createEntityGraph(Film.class);
        filmGraph.addAttributeNodes("director");

        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Film.class);
        var film = criteria.from(Film.class);

        List<Predicate> predicates = new ArrayList<>();

        if (genre != null) {
            predicates.add(cb.equal(film.get(Film_.GENRE), genre));
        }
        if (name != null) {
            predicates.add(cb.equal(film.get(Film_.NAME), name));
        }
        if (country != null) {
            predicates.add(cb.equal(film.get(Film_.COUNTRY), country));
        }

        criteria.select(film).where(
                predicates.toArray(Predicate[]::new)
        );

        var films = session.createQuery(criteria)
                .setHint(GraphSemantic.FETCH.getJakartaHintName(), filmGraph)
                .list();
        return films.stream().map(Film::getDirector).peek(System.out::println).toList();
    }

    public List<Director> findDirectorInFilmQuerydsl(Session session, FilmFilter filter) {
        var filmGraph = session.createEntityGraph(Film.class);
        filmGraph.addAttributeNodes("director");

        var predicate = QPredicate.builder()
                .add(filter.getName(), film.name::eq)
                .add(filter.getGenre(), film.genre::eq)
                .add(filter.getCountry(), film.country::eq)
                .buildOr();

        var films = new JPAQuery<Film>(session)
                .select(film)
                .from(film)
                .where(predicate)
                .setHint(GraphSemantic.FETCH.getJakartaHintName(), filmGraph)
                .fetch();

        return films.stream().map(Film::getDirector).peek(System.out::println).toList();
    }
}
