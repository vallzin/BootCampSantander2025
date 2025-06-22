--liquibase formatted sql
--changeset vallzin:202521061610
--comment: set unblock_reason nullable

ALTER TABLE blocks MODIFY COLUMN unblock_reason VARCHAR(150) NULL;

--rollback ALTER TABLE blocks MODIFY COLUMN unblock_reason VARCHAR(150) NULL;
