package com.pgr203.k9001.service;

import com.pgr203.k9001.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Accounts implements Dao<Account> {
    private final Connection connection;

    public Accounts(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (name, email) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, account.getAccountName());
            statement.setString(2, account.getEmail());
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            account.setId(generatedKeys.getLong("id"));
        }
    }

    @Override
    public Optional<Account> read(long id) {
        return Optional.empty();
    }

    public List<Account> readAll() throws SQLException {
        List<Account> result = new ArrayList<>();

        String sql = "SELECT * FROM accounts";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = new Account(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email")
                    );
                    result.add(account);
                }
            }
        }
        return result;
    }

    @Override
    public void update(Account account, String[] params) {

    }

    @Override
    public void delete(Account account) {

    }
}
