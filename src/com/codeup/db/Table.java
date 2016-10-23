/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

import com.codeup.db.builders.HasSQLRepresentation;
import com.codeup.db.statements.InsertStatement;
import com.codeup.db.statements.SelectStatement;
import com.codeup.db.statements.UpdateStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract public class Table<T> {
    private final Connection connection;

    public Table(Connection connection) {
        this.connection = connection;
    }

    public InsertStatement<T> createInsert(String... columns){
        return new InsertStatement<>(connection, table(), mapper()).columns(columns);
    }

    public UpdateStatement createUpdate(String... columns) {
        return new UpdateStatement(connection, table()).columns(columns);
    }

    public SelectStatement<T> select(String... columns) {
        return new SelectStatement<>(connection, table(), mapper()).select(columns);
    }

    public void executeUpdate(
        HasSQLRepresentation insertOrUpdate,
        Object... parameters
    ) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            insertOrUpdate.toSQL()
        )) {
            QueryParameters.bind(statement, parameters);
            statement.execute();
        }
    }

    abstract protected String table();

    abstract protected RowMapper<T> mapper();
}
