--liquibase formatted sql

--changeset valmilson:202517061211
--comment: boards table create
CREATE TABLE boards(
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
)ENGINE=InnoDB;

--rollback DROP TABLE BOARDS