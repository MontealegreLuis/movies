/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.jdbc;

import com.codeup.auth.User;
import com.codeup.auth.Users;
import com.codeup.db.Table;

import java.sql.*;

public class JdbcUsers implements Users {
    private final Table<User> table;

    public JdbcUsers(Connection connection) {
        table = new UsersTable(connection);
    }

    @Override
    public User add(User user) {
        try {
            return table
                .insert("username", "password")
                .fetch(user.username(), user.password())
            ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User identifiedBy(String username) {
        try {
            return table
                .select("*")
                .where("username = ?")
                .execute(username)
                .fetch()
            ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
