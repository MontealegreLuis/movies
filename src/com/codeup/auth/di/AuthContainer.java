/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.di;

import com.codeup.auth.jdbc.JdbcUsers;
import com.codeup.auth.Users;
import com.codeup.auth.actions.AuthenticateUser;
import com.codeup.db.di.DbContainer;

import javax.naming.NamingException;
import java.sql.SQLException;

public class AuthContainer {
    private static AuthenticateUser authenticateUser;

    public static AuthenticateUser authenticateUser() throws SQLException, NamingException {
        if (authenticateUser == null) {
            authenticateUser = new AuthenticateUser(users());
        }
        return authenticateUser;
    }

    private static Users users() throws SQLException, NamingException {
        return new JdbcUsers(DbContainer.connection());
    }
}
