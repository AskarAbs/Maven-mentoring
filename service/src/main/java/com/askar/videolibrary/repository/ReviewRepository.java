package com.askar.videolibrary.repository;

import com.askar.videolibrary.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select avg(r.evaluation) from Review r where r.film.id=:id", nativeQuery = false)
    Integer findAvgEvaluation(Long id);
}
