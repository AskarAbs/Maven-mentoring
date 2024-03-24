--liquibase formatted sql

--changeset askar:1
CREATE TABLE actor
(
    id        BIGSERIAL PRIMARY KEY,
    Full_name VARCHAR(128) NOT NULL,
    birthday  DATE         NOT NULL
);

--changeset askar:2
CREATE TABLE director
(
    id        BIGSERIAL PRIMARY KEY,
    Full_name VARCHAR(128) NOT NULL,
    birthday  DATE         NOT NULL
);

--changeset askar:3
CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(128) NOT NULL,
    email    VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    role     VARCHAR(128) NOT NULL
);

--changeset askar:4
CREATE TABLE film
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(64)  NOT NULL,
    release_date DATE         NOT NULL,
    country      VARCHAR(32)  NOT NULL,
    genre        VARCHAR(32)  NOT NULL,
    trailer      VARCHAR(128) NOT NULL,
    description  VARCHAR(256) NOT NULL,
    director_id  BIGINT       REFERENCES director (id) ON DELETE SET NULL
);

--changeset askar:5
CREATE TABLE film_actor
(
    id         BIGSERIAL PRIMARY KEY,
    actor_id   BIGINT REFERENCES actor NOT NULL,
    film_id    BIGINT REFERENCES film  NOT NULL,
    actor_role VARCHAR(32)             NOT NULL,
    fee        BIGINT                  NOT NULL,
    UNIQUE (film_id, actor_id)
);


--changeset askar:6
CREATE TABLE review
(
    id         BIGSERIAL PRIMARY KEY,
    film_id    BIGINT REFERENCES film (id) ON DELETE CASCADE  NOT NULL,
    user_id    BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    created_at TIMESTAMP                                      NOT NULL,
    text       VARCHAR(256),
    evaluation INT                                            NOT NULL,
    UNIQUE (film_id, user_id)
);

