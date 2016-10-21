/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.statements;

import com.codeup.db.QueryParameters;
import com.codeup.db.RowMapper;
import com.codeup.db.builders.queries.Insert;

import java.sql.*;

public class InsertStatement<T> {
    private final Connection connection;
    private Insert insert;
    private final RowMapper<T> mapper;

    public InsertStatement(
        Connection connection,
        String table,
        RowMapper<T> mapper
    ) {
        this.connection = connection;
        this.insert = Insert.into(table);
        this.mapper = mapper;
    }

    public InsertStatement<T> columns(String... columns) {
        insert.columns(columns);
        return this;
    }

    public T fetch(Object... parameters) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            insert.toSQL(),
            Statement.RETURN_GENERATED_KEYS
        )) {
            QueryParameters.bind(statement, parameters);
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            return mapper.newEntity(key.getInt(1), parameters);
        }
    }

    public Hydrator<T> execute(Object... parameters) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            insert.toSQL(),
            Statement.RETURN_GENERATED_KEYS
        )) {
            QueryParameters.bind(statement, parameters);
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            return new Hydrator<>(key.getLong(1), parameters, mapper);
        }
    }
}
