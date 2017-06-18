/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.jdbc;

import com.codeup.auth.Password;
import com.codeup.auth.User;
import com.codeup.db.RowMapper;

import java.util.List;

class UsersMapper implements RowMapper<User> {
    @Override
    public User mapRow(List<Object> values) {
        return new User(
            (long) values.get(0),
            values.get(1).toString(),
            Password.fromHash(values.get(2).toString())
        );
    }
}
