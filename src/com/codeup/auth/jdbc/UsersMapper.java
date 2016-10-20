/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.jdbc;

import com.codeup.auth.User;
import com.codeup.db.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class UsersMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet) throws SQLException {
        return new User(
            resultSet.getInt("id"),
            resultSet.getString("username"),
            resultSet.getString("password")
        );
    }

    @Override
    public User mapRow(Object[] values) {
        return new User(
            new Long((int) values[0]).intValue(),
            //((Long) values[0]).intValue(),
            values[1].toString(),
            values[2].toString()
        );
    }

    @Override
    public User newEntity(int id, Object[] parameters) {
        return new User(
            id,
            parameters[0].toString(),
            parameters[1].toString()
        );
    }
}
