/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.infrastructure.jdbc;

import com.codeup.auth.domain.identity.User;
import com.dbal.jdbc.RowMapper;
import com.dbal.jdbc.Table;

import java.sql.Connection;

class UsersTable extends Table<User> {
    UsersTable(Connection connection) {
        super(connection);
    }

    User insert(String username, String password) {
        return this
            .createInsert("username", "password")
            .execute(username, password)
            .fetch()
        ;
    }

    User findBy(String username) {
        return this
            .select("*")
            .where("username = ?")
            .execute(username)
            .fetch()
        ;
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
