package br.com.dio.service;

import br.com.dio.persistence.dao.BoardColumnDAO;
import br.com.dio.persistence.entity.BoardColumnEntity;
import br.com.dio.persistence.entity.BoardColumnKindEnum;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class BoardColumnQueryService {

    private final Connection connection;

    public BoardColumnQueryService(Connection connection) {
        this.connection = connection;
    }

    public Optional<BoardColumnEntity> findById(final Long id) throws SQLException {
        var dao = new BoardColumnDAO(connection);
        return dao.findById(id);
    }

    /*
    public Optional<BoardColumnEntity> findInitialColumnByBoardId(Long boardId) throws SQLException {
        var dao = new BoardColumnDAO(connection);
        var columns = dao.findByBoardId(boardId);
        return columns.stream()
                .filter(c -> c.getKind() == BoardColumnKindEnum.INITIAL)
                .findFirst();
    }
    */

}
