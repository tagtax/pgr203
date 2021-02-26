package com.pgr203.k9001.util;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    public static Connection dataSourceSetup(String url, String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        Flyway.configure().dataSource(url, user, password).load().migrate();
        return connection;
    }
}
