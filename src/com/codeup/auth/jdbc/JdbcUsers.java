/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.jdbc;

import com.codeup.auth.User;
import com.codeup.auth.Users;
import com.codeup.db.Query;
import com.codeup.db.builders.queries.Insert;
import com.codeup.db.builders.queries.Select;

import java.sql.*;

public class JdbcUsers implements Users {
    private final Query<User> query;

    public JdbcUsers(Connection connection) {
        this.query = new Query<>(connection, new UsersMapper());
    }

    @Override
    public void add(User user) {
        try {
            query.insert(
                Insert.into("users").columns("username", "password"),
                user
            );
        } catch (SQLException e) {
            throw new RuntimeException("Cannot add user", e);
        }
    }

    @Override
    public User identifiedBy(String username) {
        try {
            return query.selectOne(
                Select.from("users").where("username = ?"),
                username
            );
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find user", e);
        }
    }
}
