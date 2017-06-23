/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.authentication;

import com.codeup.auth.domain.identity.User;
import com.codeup.auth.domain.identity.Users;

public class AuthenticateUser {
    private final Users users;
    private User user;

    public AuthenticateUser(Users users) {
        this.users = users;
    }

    public boolean attemptLogin(String username, String password) {
        User user = users.identifiedBy(username);

        if (user == null || !user.passwordMatch(password)) return false;

        this.user = user;

        return true;
    }

    public User user() {
        return user;
    }
}
