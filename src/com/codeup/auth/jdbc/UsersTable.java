/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.jdbc;

import com.codeup.auth.User;
import com.codeup.db.RowMapper;
import com.codeup.db.Table;

import java.sql.Connection;

class UsersTable extends Table<User> {
    UsersTable(Connection connection) {
        super(connection);
    }

    @Override
    protected String table() {
        return "users";
    }

    @Override
    protected RowMapper<User> mapper() {
        return new UsersMapper();
    }
}
