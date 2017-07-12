/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.authentication;

import com.codeup.auth.domain.identity.Password;
import com.codeup.auth.domain.identity.User;
import com.codeup.auth.domain.identity.Users;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticateUserTest {

    @Test
    public void it_fails_to_authenticate_an_unknown_user()
    {
        String unknownUsername = "unknown";
        when(users.identifiedBy(unknownUsername)).thenReturn(null);
        Credentials credentials = withCredentials(unknownUsername, "anything");

        command.attemptLogin(credentials);

        verify(action).authenticationFailureUsing(credentials);
    }

    @Test
    public void it_fails_to_authenticate_a_registered_user_with_incorrect_password()
    {
        when(users.identifiedBy(correctUsername)).thenReturn(registeredUser);
        Credentials credentials = withCredentials(correctUsername, "wrong password");

        command.attemptLogin(credentials);

        verify(action).authenticationFailureUsing(credentials);
    }

    @Test
    public void it_authenticates_a_user_with_valid_username_and_password()
    {
        when(users.identifiedBy(correctUsername)).thenReturn(registeredUser);
        Credentials credentials = withCredentials(correctUsername, correctPassword);

        command.attemptLogin(credentials);

        verify(action).authenticationSucceededFor(registeredUser);
    }

    private Credentials withCredentials(String username, String password) {
        Map<String, String> input = new HashMap<>();
        input.put("username", username);
        input.put("password", password);
        return Credentials.from(input);
    }

    @Before
    public void configureAction() {
        correctUsername = "known";
        correctPassword = "correct password";
        registeredUser = User.registeredWith(
            1, correctUsername, Password.fromPlainText(correctPassword).toString()
        );
        action = mock(CanAuthenticateUsers.class);
        users = mock(Users.class);
        command = new AuthenticateUser(users);
        command.attach(action);
    }

    private String correctUsername;
    private String correctPassword;
    private User registeredUser;

    private Users users;
    private AuthenticateUser command;
    private CanAuthenticateUsers action;
}
