package br.com.dio;

import br.com.dio.persistence.migration.MigrationStrategy;

import java.sql.Connection;
import java.sql.SQLException;

import static br.com.dio.persistence.config.ConnectionConfig.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = getConnection()){
            new MigrationStrategy(connection).executeMigration();
        }
    }
}