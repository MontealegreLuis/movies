/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.statements;

import com.codeup.db.builders.HasSQLRepresentation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

class SQLStatement {
    protected final Connection connection;

    SQLStatement(Connection connection) {
        this.connection = connection;
    }

    RuntimeException queryException(
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
