package com.askar.videolibrary.dao;

import com.askar.videolibrary.dao.filter.QPredicate;
import com.askar.videolibrary.dto.FilmFilter;
import com.askar.videolibrary.entity.Film;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.hibernate.graph.GraphSemantic;

import java.util.List;

import static com.askar.videolibrary.entity.QFilm.film;

public class FilmDao extends BaseDao<Long, Film> {
    public FilmDao(EntityManager entityManager) {
        super(Film.class, entityManager);
    }

    public List<Film> findAll(EntityManager entityManager, FilmFilter filter) {
        var filmGraph = entityManager.createEntityGraph(Film.class);
        filmGraph.addAttributeNodes("director");

        var predicate = QPredicate.builder()
                .add(filter.getName(), film.name::eq)
                .add(filter.getGenre(), film.genre::eq)
                .add(filter.getCountry(), film.country::eq)
                .buildOr();

        return new JPAQuery<Film>(entityManager)
                .select(film)
                .from(film)
                .where(predicate)
                .setHint(GraphSemantic.FETCH.getJakartaHintName(), filmGraph)
                .fetch();
    }
}
