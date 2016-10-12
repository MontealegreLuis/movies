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

public class Query<T> {
    private final Connection connection;
    private final RowMapper<T> mapper;

    public Query(Connection connection, RowMapper<T> mapper) {
        this.connection = connection;
        this.mapper = mapper;
    }

    public void insert(Insert insert, ParametersMapper mapper) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            insert.toSQL()
        )) {
            mapper.map(statement);
            statement.executeUpdate();
        }
    }

    public int insert(Insert insert, T entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            insert.toSQL(),
            Statement.RETURN_GENERATED_KEYS
        )) {
            Mapper.map(statement, mapper.mapColumns(entity));
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
            Mapper.map(statement, mapper.mapColumns(entity));
            Mapper.map(statement, mapper.mapIdentifier(entity));
            statement.executeUpdate();
        }
    }

    public T selectOne(Select select, Object ...parameters) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            select.toSQL()
        )) {
            Mapper.map(statement, parameters);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) return null;

            return mapper.mapRow(resultSet);
        }
    }

    public List<T> selectMany(
        Select select,
        Object ...parameters
    ) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            select.toSQL()
        )) {
            Mapper.map(statement, parameters);
            ResultSet resultSet = statement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(mapper.mapRow(resultSet));
            }
            return entities;
        }
    }
}
