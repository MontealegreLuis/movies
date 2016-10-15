/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.statements;

import com.codeup.db.RowMapper;
import com.codeup.db.builders.queries.Select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectStatement<T> {
    private final Connection connection;
    private final Select select;
    private final RowMapper<T> mapper;


    public SelectStatement(
        Connection connection,
        String table,
        RowMapper<T> mapper
    ) {
        this.connection = connection;
        this.select = Select.from(table);
        this.mapper = mapper;
    }

    public SelectStatement<T> select(String... columns) {
        select.columns(columns);
        return this;
    }

    public SelectStatement<T> where(String clause) {
        select.where(clause);
        return this;
    }

    public T fetch(Object... parameters) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            select.toSQL()
        )) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) return null;

            return mapper.mapRow(resultSet);
        }
    }
}
