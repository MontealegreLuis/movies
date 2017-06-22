/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.jdbc;

import com.codeup.auth.User;
import com.codeup.auth.Users;

import java.sql.Connection;

public class JdbcUsers implements Users {
    private final UsersTable table;

    public JdbcUsers(Connection connection) {
        table = new UsersTable(connection);
    }

    @Override
    public User add(User user) {
        return table.insert(user.username().value(), user.password().toString());
    }

    @Override
    public User identifiedBy(String username) {
        return table.findBy(username);
    }
}
