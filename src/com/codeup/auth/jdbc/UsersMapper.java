/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.jdbc;

import com.codeup.auth.User;
import com.codeup.db.RowMapper;

class UsersMapper implements RowMapper<User> {
    @Override
    public User mapRow(Object[] values) {
        return new User(
            (long) values[0],
            values[1].toString(),
            values[2].toString()
        );
    }

    @Override
    public User newEntity(long id, Object[] parameters) {
        return new User(
            id,
            parameters[0].toString(),
            parameters[1].toString()
        );
    }
}
