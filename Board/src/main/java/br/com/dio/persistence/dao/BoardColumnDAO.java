package br.com.dio.persistence.dao;

import br.com.dio.dto.BoardColumnDTO;
import br.com.dio.persistence.entity.BoardColumnEntity;
import br.com.dio.persistence.entity.CardEntity;
import com.mysql.cj.jdbc.StatementImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.dio.persistence.entity.BoardColumnKindEnum.findByName;
import static java.util.Objects.isNull;

public class BoardColumnDAO {

    private final Connection connection;

    public BoardColumnDAO(Connection connection) {
        this.connection = connection;
    }

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException{

        var sql = "INSERT INTO boards_columns(name, `order`, kind, boards_id) VALUES (?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){

            var i = 1;
            statement.setString(i++,entity.getName());
            statement.setInt(i++,entity.getOrder());
            statement.setString(i++, entity.getKind().name());
            statement.setLong(i++, entity.getBoard().getId());
            statement.executeUpdate();

            if (statement instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }

            return  entity;
        }
    }

    public List<BoardColumnEntity> findByBoardId(final Long boardId) throws SQLException{

        List<BoardColumnEntity> entities = new ArrayList<>();
        var sql = "SELECT id, name, `order`, kind FROM boards_columns WHERE boards_id = ? ORDER BY `order`";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();

            while(resultSet.next()){
                var entity = new BoardColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getNString("name"));
                entity.setOrder(resultSet.getInt("order"));
                entity.setKind(findByName(resultSet.getString("kind")));
                entities.add(entity);
            }
        }
        return entities;
    }

    public List<BoardColumnDTO> findByBoardIdWithDetails(final Long boardId) throws SQLException{

        List<BoardColumnDTO> dtos = new ArrayList<>();
        var sql =
                """
                SELECT bc.id,
                       bc.name,
                       bc.kind,
                      (SELECT COUNT(c.id)
                      FROM cards c
                      WHERE c.boards_columns_id = bc.id) cards_amount
                FROM boards_columns bc
                WHERE boards_id = ?
                ORDER BY `order`;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();

            while(resultSet.next()){

                var dto = new BoardColumnDTO(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        findByName(resultSet.getString("kind")),
                        resultSet.getInt("cards_amount")
                );
                dtos.add(dto);
            }
        }
        return dtos;
    }

    public Optional<BoardColumnEntity> findById(final Long boardId) throws SQLException{

        List<BoardColumnEntity> entities = new ArrayList<>();
        var sql =
                """
                SELECT bc.name, 
                       bc.kind,
                       c.id,
                       c.title,
                       c.description 
                FROM boards_columns bc
                LEFT JOIN cards c
                ON c.boards_columns_id = bc.id
                WHERE bc.id = ? 
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();

            if(resultSet.next()){

                var entity = new BoardColumnEntity();
                entity.setName(resultSet.getNString("bc.name"));
                entity.setKind(findByName(resultSet.getString("bc.kind")));

                do {

                    if (isNull(resultSet.getString("c.title"))){
                        break;
                    }

                    var card = new CardEntity();
                    card.setId(resultSet.getLong("c.id"));
                    card.setTitle(resultSet.getString("c.title"));
                    card.setDescription(resultSet.getString("c.description"));
                    entity.getCards().add(card);

                }while(resultSet.next());

                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }
}
