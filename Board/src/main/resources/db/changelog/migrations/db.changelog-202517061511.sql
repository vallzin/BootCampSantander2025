--liquibase formatted sql

--changeset valmilson:202517061511
--comment: boards_columns table create
CREATE TABLE boards_columns(
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    `order` int NOT NULL,
    kind CHAR(7) NOT NULL,
    boards_id BIGINT NOT NULL,
    CONSTRAINT boards__boards_columns_fk FOREIGN KEY (boards_id) REFERENCES boards(id) ON DELETE CASCADE,
    CONSTRAINT id_order_uk UNIQUE KEY unique_order_id_order (boards_id, `order`)
) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS_COLUMNS