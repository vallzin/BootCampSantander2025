--liquibase formatted sql
--changeset vallzin:202521061610
--comment: blocks table create

CREATE TABLE blocks(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    blocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    block_reason VARCHAR(150) NOT NULL,
    unblocked_at TIMESTAMP NULL,
    unblock_reason TIMESTAMP NULL,
    card_id BIGINT NOT NULL,
    CONSTRAINT cards__blocks_fk FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE
)ENGINE=innoDB;

--rollback DROP TABLE BLOCKS
