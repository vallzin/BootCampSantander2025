--liquibase formatted sql

--changeset valmilson:202517061511
--comment: cards table create
CREATE TABLE cards(
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    boards_columns_id BIGINT NOT NULL,
    CONSTRAINT boards_columns__cards_fk FOREIGN KEY (boards_columns_id) REFERENCES boards_columns (id) ON DELETE CASCADE
)ENGINE=InnoDB;

--rollback DROP TABLE CARDS