package com.askar.videolibrary.entityCrud;

import com.askar.videolibrary.dao.filter.CPredicate;
import com.askar.videolibrary.dao.filter.QPredicate;
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

import java.util.List;

import static com.askar.videolibrary.entity.QFilm.film;
import static org.assertj.core.api.Assertions.assertThat;


public class QueryFilterIT {

    private static SessionFactory sessionFactory;
    private Session session;

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
        sessionFactory.close();
    }

    @Test
    void checkFindDirectorInFilmWhereGenreIsNullCriteria() {
        var directors2 = findDirectorInFilmQuerydsl(session, FilmFilter.builder()
                .genre(Genre.ADVENTURES)
                .country(null)
                .name(null)
                .build());
        assertThat(directors2).hasSize(1);
    }

    @Test
    void checkFindDirectorInFilmWhereNameIsNullCriteria() {
        var directors2 = findDirectorInFilmQuerydsl(session, FilmFilter.builder()
                .genre(null)
                .country(null)
                .name("Iron Man")
                .build());
        assertThat(directors2).hasSize(1);
    }

    @Test
    void checkFindDirectorInFilmWhereCountryIsNullCriteria() {
        var directors2 = findDirectorInFilmQuerydsl(session, FilmFilter.builder()
                .genre(null)
                .country("USA")
                .name(null)
                .build());
        assertThat(directors2).hasSize(2);
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

    public List<Director> findDirectorInFilmCriteria(Session session, FilmFilter filter) {
        var filmGraph = session.createEntityGraph(Film.class);
        filmGraph.addAttributeNodes("director");

        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Film.class);
        var film = criteria.from(Film.class);

        var predicate = CPredicate.builder()
                .add(cb, film.get(Film_.GENRE), filter.getGenre())
                .add(cb, film.get(Film_.NAME), filter.getName())
                .add(cb, film.get(Film_.COUNTRY), filter.getCountry())
                .buildOr();

        criteria.select(film).where((Predicate) predicate);

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
