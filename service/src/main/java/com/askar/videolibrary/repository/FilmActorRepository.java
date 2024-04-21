package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Actor;
import com.askar.videolibrary.entity.FilmActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FilmActorRepository extends JpaRepository<FilmActor, Long> {


    @Query(value = "SELECT a.full_name " +
                   "FROM film_actor f " +
                   "join actor a on a.id = f.actor_id " +
                   "where film_id =: filmId ", nativeQuery = true)
    Actor findFilmActorByFilmId(@Param("filmId") Long filmId);

}
