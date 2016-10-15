/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.jdbc;

import com.codeup.auth.User;
import com.codeup.db.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class UsersMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet) throws SQLException {
        return new User(
            resultSet.getInt("id"),
            resultSet.getString("username"),
            resultSet.getString("password")
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

    @Override
    public Map<Integer, Object> mapColumns(User user) {
        Map<Integer, Object> mapping = new LinkedHashMap<>();
        mapping.put(1, user.username());
        mapping.put(2, user.password());
        return mapping;
    }

    @Override
    public Map<Integer, Object> mapIdentifier(User user) {
        Map<Integer, Object> mapping = new LinkedHashMap<>();
        mapping.put(3, user.id());
        return mapping;
    }
}
