--liquibase formatted sql

--changeset askar:1
ALTER TABLE film
    ADD COLUMN image VARCHAR(64);

--changeset askar:2
ALTER TABLE users
    ADD COLUMN image VARCHAR(64);
