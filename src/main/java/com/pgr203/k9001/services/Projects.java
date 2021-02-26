package com.pgr203.k9001.services;

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
        Optional<Project> project = Optional.empty();

        String sql = "SELECT * FROM projects WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    project = Optional.of(new Project(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getBoolean("status")
                    ));
                }
            }
        }
        return project;
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
    public void update(Project project, String[] params) throws SQLException {
        String sql = "UPDATE projects SET status = 't' WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, project.getId());
            statement.executeQuery();
        }
    }

    @Override
    public void delete(Project project) throws SQLException {
        connection.beginRequest();

        {
            String sql = "DELETE FROM project_accounts WHERE project_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, project.getId());
                statement.executeQuery();
            }
        }

        {
            String sql = "DELETE FROM projects WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, project.getId());
                statement.executeQuery();
            }
        }

        connection.commit();
    }

    public List<Project> getTaskList(long id) throws SQLException {
        List<Project> result = new ArrayList<>();

        String sql = "SELECT p.name, p.status FROM project_accounts pa JOIN accounts a on a.id = pa.account_id JOIN projects p on p.id = pa.project_id WHERE a.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Project project = new Project();
                    project.setProjectName(resultSet.getString("name"));
                    project.setStatus(resultSet.getBoolean("status"));
                    result.add(project);
                }
            }
        }
        return result;
    }

    public void joinProject(long projectId, long accountId) throws SQLException {
        String sql = "SELECT COUNT(project_id) FROM project_accounts WHERE project_id = ? AND account_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, projectId);
            statement.setLong(2, accountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    if (resultSet.getInt("count") == 0) {
                        String insertSql = "INSERT INTO project_accounts (project_id, account_id) VALUES (?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                            insertStatement.setLong(1, projectId);
                            insertStatement.setLong(2, accountId);
                            insertStatement.execute();
                        }
                    }
                }
            }
        }
    }
}
