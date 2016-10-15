/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.statements;

import com.codeup.db.builders.queries.Insert;

import java.sql.*;

/**
 * int id = Insert
 *     .into("users")
 *     .columns("username", "password")
 *     .execute("luis", "changeme");
 */
public class InsertStatement {
    private final Connection connection;
    private Insert insert;

    public InsertStatement(Connection connection, Insert insert) {
        this.connection = connection;
        this.insert = insert;
    }

    public static InsertStatement into(Connection connection, String table) {
        return new InsertStatement(connection, Insert.into(table));
    }

    public InsertStatement columns(String... columns) {
        insert.columns(columns);
        return this;
    }

    public int execute(Object... parameters) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
            insert.toSQL(),
            Statement.RETURN_GENERATED_KEYS
        )) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i+1, parameters[i]);
            }
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            return key.getInt(1);
        }
    }
}
