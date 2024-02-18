package com.askar.videoLibrary.entity;

import com.askar.videoLibrary.entity.enums.ActorRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "film_actor")
public class FilmActor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Actor actor;

    @ManyToOne
    private Film film;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActorRole actorRole;

    @Column(nullable = false)
    private Long fee;

    public void setFilm(Film film) {
        this.film = film;
        this.film.getFilmActors().add(this);
    }

    public void setActors(Actor actor) {
        this.actor = actor;
        this.actor.getFilmActors().add(this);
    }

}
