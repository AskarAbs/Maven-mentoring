--liquibase formatted sql

--changeset askar:1
ALTER TABLE review
    ALTER COLUMN modified_by SET DATA TYPE VARCHAR(128);

ALTER TABLE review
    ALTER COLUMN created_by SET DATA TYPE VARCHAR(128);

ALTER TABLE users
    ALTER COLUMN created_by SET DATA TYPE VARCHAR(128);

ALTER TABLE users
    ALTER COLUMN modified_by SET DATA TYPE VARCHAR(128);