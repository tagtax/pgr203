package com.pgr203.k9001.service;

import com.pgr203.k9001.model.Account;
import com.pgr203.k9001.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Projects implements Dao<Project> {
    private final Connection connection;

    public Projects(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Project project) throws SQLException {
        String sql = "INSERT INTO projects (name, status) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, project.getProjectName());
            statement.setBoolean(2, project.getStatus());
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            project.setId(generatedKeys.getLong("id"));
        }
    }

    @Override
    public Optional<Project> read(long id) throws SQLException {
        return Optional.empty();
    }

    public List<Project> readAll() throws SQLException {
        List<Project> result = new ArrayList<>();

        String sql = "SELECT * FROM projects";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Project project = new Project(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getBoolean("status")
                    );
                    result.add(project);
                }
            }
        }
        return result;
    }

    @Override
    public void update(Account account, String[] params) throws SQLException {

    }

    @Override
    public void delete(Account account) throws SQLException {

    }
}
