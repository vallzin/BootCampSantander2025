--liquibase formatted sql
--changeset vallzin:202521061349
--comment: boards table create

CREATE TABLE boards(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL
)ENGINE=innoDB;

--rollback DROP TABLE BOARDS
