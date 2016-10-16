/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.statements;

import com.codeup.db.builders.queries.Update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateStatement {
    private final Connection connection;
    private final Update update;

    public UpdateStatement(Connection connection, String table) {
        this.connection = connection;
        this.update = Update.table(table);
    }

    public UpdateStatement columns(String... columns) {
        update.columns(columns);
        return this;
    }

    public void execute(Object... parameters) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            update.toSQL()
        )) {
            bindParameters(statement, parameters);
            statement.executeUpdate();
        }
    }

    public UpdateStatement where(String clause) {
        update.where(clause);
        return this;
    }

    private void bindParameters(
        PreparedStatement statement,
        Object[] parameters
    ) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i+1, parameters[i]);
        }
    }
}
