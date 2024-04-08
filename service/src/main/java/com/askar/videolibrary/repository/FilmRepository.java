package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FilmRepository extends JpaRepository<Film, Long>,
        FilmFilterRepository, QuerydslPredicateExecutor<Film> {

}
