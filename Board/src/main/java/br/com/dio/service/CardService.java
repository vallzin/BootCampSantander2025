package br.com.dio.service;

import br.com.dio.dto.BoardColumnInfoDTO;
import br.com.dio.exception.CardBlockException;
import br.com.dio.exception.CardFinishedException;
import br.com.dio.exception.EntityNotFoundException;
import br.com.dio.persistence.dao.CardDAO;
import br.com.dio.persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static br.com.dio.persistence.entity.BoardColumnKindEnum.FINAL;

public class CardService {

    private final Connection connection;

    public CardService(Connection connection) {
        this.connection = connection;
    }

    public CardEntity insert(final CardEntity entity) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            dao.insert(entity);
            connection.commit();
            return entity;
        }catch (SQLException e){
            connection.rollback();
            throw e;
        }

    }

    public void moveToNextColumn(final Long cardId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {

        try {
            CardDAO dao = new CardDAO(connection);
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(() -> new EntityNotFoundException("O card id %s não foi encontrado".formatted(cardId)));
            if (dto.blocked()){
                var message = "O card %s está bloqueado, é necessário desbloqueá-lo para mover.".formatted(cardId);
                throw new CardBlockException(message);
            }
            var currentColumn = boardColumnsInfo
                    .stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("O card informado pertence a outro board"));
            if (currentColumn.kind().equals(FINAL)){
                throw new CardFinishedException("O card já foi finalizado");
            }
            var nextColumn = boardColumnsInfo
                    .stream()
                    .filter(bc -> bc.order() == currentColumn.order() + 1)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Card cancelado"));
            dao.moveToColumn(nextColumn.id(), cardId);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void cancel(final Long cardId,final Long cancelColumnId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(() -> new EntityNotFoundException("Card de id %s cancelado".formatted(cardId)));
            if (dto.blocked()){
                var message = "O card %s está bloqueado, é necessário desbloqueá-lo para mover.".formatted(cardId);
                throw new CardBlockException(message);
            }
            var currentColumn = boardColumnsInfo
                    .stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("O card informado pertence a outro board"));
            if (currentColumn.kind().equals(FINAL)){
                throw new CardFinishedException("O card já foi finalizado");
            }

            boardColumnsInfo
                    .stream()
                    .filter(bc -> bc.order() == currentColumn.order() + 1)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Card cancelado"));
            dao.moveToColumn(cancelColumnId, cardId);
            connection.commit();
        }catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }


}

