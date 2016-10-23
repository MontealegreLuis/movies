/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.statements;

import com.codeup.db.QueryParameters;
import com.codeup.db.builders.queries.Update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateStatement extends SQLStatement {
    private final Update update;

    public UpdateStatement(Connection connection, String table) {
        super(connection);
        this.update = Update.table(table);
    }

    public UpdateStatement columns(String... columns) {
        update.columns(columns);
        return this;
    }

    public UpdateStatement where(String clause) {
        update.where(clause);
        return this;
    }

    public void execute(Object... parameters) {
        try (PreparedStatement statement = connection.prepareStatement(
            update.toSQL()
        )) {
            QueryParameters.bind(statement, parameters);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw queryException(update, parameters, e);
        }
    }
}
