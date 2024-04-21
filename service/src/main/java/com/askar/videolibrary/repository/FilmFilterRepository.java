package com.askar.videolibrary.repository;

import com.askar.videolibrary.dto.film.FilmFilter;
import com.askar.videolibrary.entity.Film;

import java.util.List;

public interface FilmFilterRepository {

    List<Film> findFilmByFilmFilter(FilmFilter filter);
}
