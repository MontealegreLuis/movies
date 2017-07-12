/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.authentication;

import com.codeup.auth.domain.identity.User;
import com.codeup.auth.domain.identity.Users;

public class AuthenticateUser {
    private final Users users;
    private CanAuthenticateUsers action;

    public AuthenticateUser(Users users) {
        this.users = users;
    }

    public void attach(CanAuthenticateUsers action) {
        this.action = action;
    }

    public void attemptLogin(Credentials credentials) {
        User user = users.identifiedBy(credentials.username());

        if (isARegistered(user) && user.passwordMatch(credentials.password())) {
            action().authenticationSucceededFor(user);
        } else {
            action().authenticationFailureUsing(credentials);
        }
    }

    private boolean isARegistered(User user) {
        return user != null;
    }

    private CanAuthenticateUsers action() {
        if (action == null) throw new IllegalStateException("No action has been attached to this command");
        return action;
    }
}
