/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.application.authentication;

import com.codeup.auth.domain.authentication.AuthenticateUser;
import com.codeup.auth.domain.authentication.CanAuthenticateUsers;
import com.codeup.auth.domain.authentication.Credentials;
import com.codeup.auth.domain.identity.User;

public class LoginAction implements CanAuthenticateUsers {
    private LoginResponder responder;
    private LoginInput input;
    private AuthenticateUser command;

    public LoginAction(LoginResponder responder, LoginInput input, AuthenticateUser command) {
        this.responder = responder;
        this.input = input;
        this.command = command;
        this.command.attach(this);
    }

    public void inputLoginCredentials() {
        responder.respondToInputLoginCredentials();
    }

    public void attemptLogin(String username, String password) {
        if (input.isValid(username, password))
            command.attemptLogin(Credentials.from(input.values()));
        else
            responder.respondToInvalidLoginInput(input);
    }

    @Override
    public void authenticationFailureUsing(Credentials credentials) {
        responder.respondToInvalidLoginAttemptWith(credentials);
    }

    @Override
    public void authenticationSucceededFor(User user) {
        responder.respondToASuccessfulAuthenticationOf(user);
    }
}
