package br.com.dio.persistence.config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//@NoArgsConstructor(access = PRIVATE) --> Anotação lombok que gera um construtor
// sem argumentos com modificador de acesso privado(construtor privado)
public final class ConnectionConfig {

    private ConnectionConfig(){}

    private static final String url = "jdbc:mysql://localhost/board";
    private static final String user = "vallzin";
    private static final String password = "valval";

    public static Connection getConnection(){

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter conexão com o banco de dados ",e);
        }

    }
}
