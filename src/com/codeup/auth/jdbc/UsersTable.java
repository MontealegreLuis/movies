/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.jdbc;

import com.codeup.auth.User;
import com.codeup.db.RowMapper;
import com.codeup.db.Table;

import java.sql.Connection;
import java.sql.SQLException;

class UsersTable extends Table<User> {
    UsersTable(Connection connection) {
        super(connection);
    }

    User insert(String username, String password) {
        try {
            return this
                .createInsert("username", "password")
                .execute(username, password)
                .fetch()
            ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    User findBy(String username) {
        try {
            return this
                .select("*")
                .where("username = ?")
                .execute(username)
                .fetch()
            ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
