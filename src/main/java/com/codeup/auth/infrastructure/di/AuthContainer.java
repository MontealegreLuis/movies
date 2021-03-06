/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.infrastructure.di;

import com.codeup.auth.domain.authentication.SignUpUser;
import com.codeup.auth.infrastructure.jdbc.JdbcUsers;
import com.codeup.auth.domain.identity.Users;
import com.codeup.auth.domain.authentication.AuthenticateUser;
import com.codeup.db.di.DbContainer;

import javax.naming.NamingException;
import java.sql.SQLException;

public class AuthContainer {
    private static AuthenticateUser authenticateUser;
    private static SignUpUser signUpUser;

    public static AuthenticateUser authenticateUser() throws SQLException, NamingException {
        if (authenticateUser == null) authenticateUser = new AuthenticateUser(users());

        return authenticateUser;
    }

    public static SignUpUser signupUser() throws SQLException, NamingException {
        if (signUpUser == null) signUpUser = new SignUpUser(users());

        return signUpUser;
    }

    private static Users users() throws SQLException, NamingException {
        return new JdbcUsers(DbContainer.connection());
    }
}
