package com.pgr203.k9001.services;

import java.sql.SQLException;
import java.util.Optional;

public interface Dao<T> {
    void create(T t) throws SQLException;

    Optional<T> read(long id) throws SQLException;

    void update(T t, String[] params) throws SQLException;

    void delete(T t) throws SQLException;
}
