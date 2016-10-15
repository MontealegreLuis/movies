/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

import com.codeup.db.statements.InsertStatement;

import java.sql.Connection;

/**
 * Table gateway implementation
 *
 * - insert, update, select, delete
 * - fetchOne, fetchAll, fetchInt
 *
 * Tables users = new Table(connection, "users")
 * // User user = // ?
 * users.insert("username", "password").execute("luis", "changeme");
 */
public class Table {
    private final Connection connection;
    private final String table;

    public Table(Connection connection, String table) {
        this.connection = connection;
        this.table = table;
    }

    public InsertStatement insert(String... columns){
        return InsertStatement.into(connection, table).columns(columns);
    }
}
