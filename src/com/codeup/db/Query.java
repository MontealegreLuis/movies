/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

import com.codeup.db.builders.queries.Insert;
import com.codeup.db.builders.queries.Select;
import com.codeup.db.builders.queries.Update;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    public void update(Update update, T entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            update.toSQL()
        )) {
            mapColumns(entity, statement);
            mapIdentifier(entity, statement);
            statement.executeUpdate();
        }
    }

    public T selectOne(Select select, Object ...parameters) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            select.toSQL()
        )) {
            mapParameters(statement, parameters);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return mapper.mapRow(resultSet);
        }
    }

    public List<T> selectMany(Select select, Object ...parameters) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            select.toSQL()
        )) {
            mapParameters(statement, parameters);
            ResultSet resultSet = statement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(mapper.mapRow(resultSet));
            }
            return entities;
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
        setParameters(statement, columns);
    }

    private void mapIdentifier(T entity, PreparedStatement statement) {
        Map<Integer, Object> columns = mapper.mapIdentifier(entity);
        setParameters(statement, columns);
    }

    private void setParameters(PreparedStatement statement, Map<Integer, Object> columns) {
        columns.forEach((k, v) -> {
            try {
                statement.setObject(k.intValue(), v);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
