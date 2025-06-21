package br.com.dio.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionConfig {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost/board";
        String user = "vallzin";
        String password = "valval";
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        return connection;

    }
}
