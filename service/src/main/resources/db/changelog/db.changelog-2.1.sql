--liquibase formatted sql

--changeset askar:1
ALTER TABLE review
    ADD COLUMN modified_by TIMESTAMP;

ALTER TABLE review
    ADD COLUMN created_by TIMESTAMP;

ALTER TABLE users
    ADD COLUMN created_by TIMESTAMP;

ALTER TABLE users
    ADD COLUMN modified_by TIMESTAMP;