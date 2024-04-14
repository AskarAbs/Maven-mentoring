--liquibase formatted sql

--changeset askar:1
ALTER TABLE film
    ALTER COLUMN description SET DATA TYPE VARCHAR(1024);


