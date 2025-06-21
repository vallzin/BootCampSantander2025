package br.com.dio.persistence.dao;

import br.com.dio.dto.CardDetailsDTO;
import br.com.dio.persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static br.com.dio.persistence.converter.OffsetDateTimeConverter.toOffsetDateTime;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Objects.nonNull;

public class CardDAO {

    private final Connection connection;

    public CardDAO(Connection connection) {
        this.connection = connection;
    }


    public CardEntity insert(final CardEntity entity) throws SQLException {

        var sql = "INSERT INTO cards (title, description, boards_columns_id) VALUES (?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)){

            int i = 1;
            statement.setString(i++, entity.getTitle());
            statement.setString(i++, entity.getDescription());
            statement.setLong(i++, entity.getBoardColumn().getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0){
                throw new SQLException("Falha ao inserir card, nenhuma linha afetada.");
            }

            try (var generatedKeys = statement.getGeneratedKeys()){
                if (generatedKeys.next()){
                    entity.setId(generatedKeys.getLong(1));
                }else {
                    throw new SQLException("Falha ao obter ID do card inserido.");
                }
            }
            /*
            if (statement instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }*/
        }
        return entity;
    }

    public void moveToColumn(final Long columnId, final Long cardId){
        var sql = "UPDATE cards SET boards_columns_id = ? WHERE id = ?;";
        try(var statement = connection.prepareStatement(sql)){
            var i = 1;
            statement.setLong(i++, columnId);
            statement.setLong(i, cardId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public Optional<CardDetailsDTO> findById(final Long id) throws SQLException {

        var sql =
                """
                SELECT c.id,
                       c.title,
                       c.description,
                       b.blocked_at,
                       b.block_reason,
                       c.boards_columns_id,
                       bc.name,
                      (
                      SELECT COUNT(sub_b.id)
                      FROM blocks sub_b
                      WHERE sub_b.cards_id = c.id
                ) blocks_amount
                FROM cards c
                LEFT JOIN blocks b
                    ON c.id = b.cards_id
                    AND b.unblocked_at IS NULL
                INNER JOIN boards_columns bc
                    ON bc.id = c.boards_columns_id
                WHERE c.id = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();

            if (resultSet.next()){

                var dto = new CardDetailsDTO(
                        resultSet.getLong("c.id"),
                        resultSet.getString("c.title"),
                        resultSet.getString("c.description"),
                        nonNull(resultSet.getString("b.block_reason")),
                        toOffsetDateTime(resultSet.getTimestamp("b.blocked_at")),
                        resultSet.getString("b.block_reason"),
                        resultSet.getInt("blocks_amount"),
                        resultSet.getLong("c.boards_columns_id"),
                        resultSet.getString("bc.name")
                );
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }
}
