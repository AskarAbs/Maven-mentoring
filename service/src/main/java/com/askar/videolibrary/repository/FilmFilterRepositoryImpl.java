package com.askar.videolibrary.repository;

import com.askar.videolibrary.dto.film.FilmFilter;
import com.askar.videolibrary.entity.Film;
import com.askar.videolibrary.repository.filter.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

import static com.askar.videolibrary.entity.QFilm.film;

@RequiredArgsConstructor
public class FilmFilterRepositoryImpl implements FilmFilterRepository {

    private final EntityManager entityManager;

    @Override
    @EntityGraph("director")
    public List<Film> findFilmByFilmFilter(FilmFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getName(), film.name::eq)
                .add(filter.getGenre(), film.genre::eq)
                .add(filter.getCountry(), film.country::eq)
                .buildOr();

        return new JPAQuery<Film>(entityManager)
                .select(film)
                .from(film)
                .where(predicate)
                .fetch();
    }
}
