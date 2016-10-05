/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

import com.codeup.db.builders.queries.Insert;
import com.codeup.db.builders.queries.Select;
import com.codeup.movies.jdbc.RowMapper;

import java.sql.*;
import java.util.Map;

public class Query<T> {
    private final Connection connection;
    private final RowMapper<T> mapper;

    public Query(Connection connection, RowMapper<T> mapper) {
        this.connection = connection;
        this.mapper = mapper;
    }

    public int insert(Insert insert, T entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            insert.toSQL(),
            Statement.RETURN_GENERATED_KEYS
        )) {
            mapColumns(entity, statement);
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            return key.getInt(1);
        }
    }

    public T selectOne(Select select, Object ...parameters) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            select.toSQL()
        )) {
            mapParameters(statement, parameters);
            return mapper.mapRow(statement.executeQuery());
        }
    }

    private void mapParameters(PreparedStatement statement, Object[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            try {
                statement.setObject(i + 1, parameters[i]);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void mapColumns(T entity, PreparedStatement statement) {
        Map<Integer, Object> columns = mapper.mapColumns(entity);
        columns.forEach((k, v) -> {
            try {
                statement.setObject(k.intValue(), v);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
