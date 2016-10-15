/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

import com.codeup.db.statements.InsertStatement;
import com.codeup.db.statements.SelectStatement;

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
abstract public class Table<T> {
    private final Connection connection;

    public Table(Connection connection) {
        this.connection = connection;
    }

    public InsertStatement<T> insert(String... columns){
        return new InsertStatement<>(connection, table(), mapper()).columns(columns);
    }

    public SelectStatement<T> select(String... columns) {
        return new SelectStatement<>(connection, table(), mapper()).select(columns);
    }

    abstract protected String table();

    abstract protected RowMapper<T> mapper();
}
