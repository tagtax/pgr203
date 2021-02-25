package com.pgr203.k9001.services;

import com.pgr203.k9001.model.Account;

import java.sql.SQLException;
import java.util.Optional;

public interface Dao<T> {
    void create(T t) throws SQLException;

    Optional<T> read(long id) throws SQLException;

    void update(Account account, String[] params) throws SQLException;

    void delete(Account account) throws SQLException;
}
