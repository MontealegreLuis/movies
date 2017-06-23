/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.infrastructure.jdbc;

import com.codeup.auth.domain.identity.User;
import com.codeup.db.RowMapper;

import java.util.List;

class UsersMapper implements RowMapper<User> {
    @Override
    public User mapRow(List<Object> values) {
        return User.registeredWith(
            (long) values.get(0),
            values.get(1).toString(),
            values.get(2).toString()
        );
    }
}
