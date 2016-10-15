/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.jdbc;

import com.codeup.db.RowMapper;
import com.codeup.db.Table;

import java.sql.Connection;

public class UsersTable<User> extends Table<User> {
    public UsersTable(Connection connection) {
        super(connection);
    }

    @Override
    public String table() {
        return "users";
    }

    public RowMapper<User> mapper() {
        return (RowMapper<User>) new UsersMapper();
    }
}
