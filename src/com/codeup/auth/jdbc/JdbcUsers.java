/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.jdbc;

import com.codeup.auth.User;
import com.codeup.auth.Users;
import com.codeup.db.Select;

import java.sql.*;

public class JdbcUsers implements Users {
    private final Connection connection;

    public JdbcUsers(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (username, password) VALUES (?, ?)"
            );
            statement.setString(1, user.username());
            statement.setString(2, user.password());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot add user", e);
        }
    }

    @Override
    public User identifiedBy(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                Select.from("users").where("username = ?").toSQL()
            );
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find user", e);
        }
    }
}
