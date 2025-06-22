package br.com.dio.service;

import br.com.dio.persistence.dao.CardDAO;
import br.com.dio.persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.SQLException;

public class CardService {

    private final Connection connection;

    public CardService(Connection connection) {
        this.connection = connection;
    }

    public CardEntity create(final CardEntity entity) throws SQLException {
        try {
            CardDAO dao = new CardDAO(connection);
            dao.insert(entity);
            connection.commit();
            return entity;
        } catch (SQLException ex){
            connection.rollback();
            throw ex;
        }
    }
}
