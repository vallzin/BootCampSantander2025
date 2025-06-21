--liquibase formatted sql

--changeset valmilson:202517061511
--comment: Criação da tabela blocks com referência a cards e boards_columns
CREATE TABLE blocks (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    blocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    block_reason VARCHAR(255) NOT NULL,
    unblocked_at TIMESTAMP NULL,
    unblock_reason VARCHAR(255) NULL,
    boards_columns_id BIGINT NOT NULL,
    cards_id BIGINT NOT NULL,
    CONSTRAINT blocks_boards_columns_fk FOREIGN KEY (boards_columns_id) REFERENCES boards_columns(id) ON DELETE CASCADE,
    CONSTRAINT blocks_cards_fk FOREIGN KEY (cards_id) REFERENCES cards(id) ON DELETE CASCADE
) ENGINE=InnoDB;

--rollback DROP TABLE blocks;