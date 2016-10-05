/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

import com.codeup.db.builders.queries.Insert;
import com.codeup.movies.jdbc.RowMapper;

import java.sql.*;
import java.util.Map;

public class Query<T> {
    private final Connection connection;

    public Query(Connection connection) {
        this.connection = connection;
    }

    public int insert(Insert insert, T entity, RowMapper mapper) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            insert.toSQL(),
            Statement.RETURN_GENERATED_KEYS
        )) {
            mapColumns(entity, mapper, statement);
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            return key.getInt(1);
        }
    }

    private void mapColumns(T entity, RowMapper mapper, PreparedStatement statement) {
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
