package com.pgr203.k9001;

import com.pgr203.k9001.controllers.*;
import com.pgr203.k9001.http.HttpServer;
import com.pgr203.k9001.service.Accounts;
import com.pgr203.k9001.service.Projects;
import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Properties;

public class PGR203ServerService extends PGR203ServerConfiguration {
    public void initialize() {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("pgr203.properties"));

            Enumeration<?> enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                System.setProperty(key, properties.getProperty(key));
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void run() throws Exception {
        this.initialize();

        Connection dataSourceConnection = DriverManager.getConnection(
                getDataSourceConfiguration().getUrl(),
                getDataSourceConfiguration().getUsername(),
                getDataSourceConfiguration().getPassword()
        );

        Flyway.configure().dataSource(
                getDataSourceConfiguration().getUrl(),
                getDataSourceConfiguration().getUsername(),
                getDataSourceConfiguration().getPassword()
        ).load().migrate();

        Accounts accounts = new Accounts(dataSourceConnection);
        Projects projects = new Projects(dataSourceConnection);

        FileController fileController = new FileController("src/main/resources/public/");
        AccountCreateController accountCreateController = new AccountCreateController(accounts);
        AccountListController accountListController = new AccountListController(accounts);
        ProjectCreateController projectCreateController = new ProjectCreateController(projects);
        ProjectListController projectListController = new ProjectListController(projects);

        HttpServer httpServer = new HttpServer(8080);
        httpServer.setDefaultHandler(fileController);
        httpServer.handle("/api/members", accountCreateController);
        httpServer.handle("/api/projectMembers", accountListController);
        httpServer.handle("/api/projects", projectCreateController);
        httpServer.handle("/api/projectList", projectListController);
        httpServer.listenAndServe();
    }

    public static void main(String[] args) throws Exception {
        new PGR203ServerService().run();
    }
}
