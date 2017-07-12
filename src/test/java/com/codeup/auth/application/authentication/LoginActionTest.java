/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.application.authentication;

import com.codeup.auth.domain.authentication.AuthenticateUser;
import com.codeup.auth.domain.authentication.Credentials;
import com.codeup.auth.domain.identity.Password;
import com.codeup.auth.domain.identity.User;
import com.codeup.auth.domain.identity.Users;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginActionTest {
    @Test
    public void it_allows_users_to_input_their_credentials()
    {
        action.inputLoginCredentials();

        verify(responder).respondToInputLoginCredentials();
    }

    @Test
    public void it_provides_feedback_when_the_credentials_are_invalid()
    {
        action.attemptLogin(null, null);

        verify(responder).respondToInvalidLoginInput(input);
        assertThat(input.messages().size(), is(greaterThan(0)));
    }

    @Test
    public void it_provides_feedback_when_the_authentication_fails()
    {
        when(users.identifiedBy("luis")).thenReturn(null);

        action.attemptLogin("not_luis", "password does not really matter");

        verify(responder).respondToInvalidLoginAttemptWith(any(Credentials.class));
    }

    @Test
    public void it_provides_feedback_if_authentication_succeeds()
    {
        Password password = Password.fromPlainText("password!");
        User registeredUser = User.registeredWith(1, "luis", password.toString());
        when(users.identifiedBy("luis")).thenReturn(registeredUser);

        action.attemptLogin("luis", "password!");

        verify(responder).respondToASuccessfulAuthenticationOf(registeredUser);
    }

    @Before
    public void configureAction() {
        responder = mock(LoginResponder.class);
        input = new LoginInput();
        users = mock(Users.class);
        action = new LoginAction(responder, input, new AuthenticateUser(users));
    }

    private LoginAction action;
    private LoginResponder responder;
    private LoginInput input;
    private Users users;
}