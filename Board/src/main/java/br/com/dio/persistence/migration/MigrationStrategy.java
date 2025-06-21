package br.com.dio.persistence.migration;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import static br.com.dio.persistence.config.ConnectionConfig.getConnection;

public class MigrationStrategy {

    private final Connection connection;

    public MigrationStrategy(Connection connection) {
        this.connection = connection;
    }

    public void executeMigration(){
        var originalOut = System.out;
        var originalErr = System.err;
        try {
            try(var fos = new FileOutputStream("liquibase.log")){
                System.setOut(new PrintStream(fos));
                System.setErr(new PrintStream(fos));
                try (
                        Connection connection = getConnection();
                        var jdbcConnection = new JdbcConnection(this.connection);){
                    Liquibase  liquibase = new Liquibase(
                            "/db/changelog/db.changelog-master.yml",
                            new ClassLoaderResourceAccessor(),
                            jdbcConnection);
                    liquibase.update();
                    connection.commit();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException | LiquibaseException e) {
            e.printStackTrace();
        }finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }

    }
}
