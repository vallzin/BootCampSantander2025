package br.com.dio.persistence.migration;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.exception.LiquibaseException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import static br.com.dio.persistence.config.ConnectionConfig.getConnection;

public class MigrationStrategy {

    public MigrationStrategy(Connection connection) {
    }

    public void executeMigration() {
        var originalOut = System.out;
        var originalErr = System.err;

        try (var fos = new FileOutputStream("liquibase.log")) {
            System.setOut(new PrintStream(fos));
            System.setErr(new PrintStream(fos));
            try (
                    var connection = getConnection();
                    var jdbcConnection = new JdbcConnection(connection)
            ) {
                var liquibase = new Liquibase(
                        "db/changelog/db.changelog-master.yml",
                        new ClassLoaderResourceAccessor(),
                        jdbcConnection
                );
                liquibase.update();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (LiquibaseException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }
}
