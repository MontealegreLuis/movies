/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.statements;

import com.codeup.db.QueryParameters;
import com.codeup.db.RowMapper;
import com.codeup.db.builders.HasSQLRepresentation;
import com.codeup.db.builders.queries.Insert;

import java.sql.*;
import java.util.Arrays;

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

    public Hydrator<T> execute(Object... parameters) {
        try (PreparedStatement statement = connection.prepareStatement(
            insert.toSQL(),
            Statement.RETURN_GENERATED_KEYS
        )) {
            QueryParameters.bind(statement, parameters);
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            return new Hydrator<>(key.getLong(1), parameters, mapper);
        } catch (SQLException e) {
            throw queryException(insert, parameters, e);
        }
    }

    private RuntimeException queryException(
        HasSQLRepresentation statement,
        Object[] parameters,
        SQLException cause
    ) {
        return new RuntimeException(
            String.format(
                "Cannot execute statement %s with parameters %s",
                statement.toSQL(),
                Arrays.toString(parameters)
            ),
            cause
        );
    }
}
