/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.authentication;

import com.codeup.auth.domain.identity.Password;
import com.codeup.auth.domain.identity.User;
import com.codeup.auth.domain.identity.Users;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticateUserTest {

    @Test
    public void it_fails_to_authenticate_an_unknown_user()
    {
        when(users.identifiedBy("unknown")).thenReturn(null);

        assertThat(action.attemptLogin("unknown", "anything"), is(false));
        assertThat(action.user(), is(nullValue()));
    }

    @Test
    public void it_fails_to_authenticate_a_registered_user_with_incorrect_password()
    {
        when(users.identifiedBy(correctUsername)).thenReturn(registeredUser);

        assertThat(action.attemptLogin(correctUsername, "wrong password"), is(false));
        assertThat(action.user(), is(nullValue()));
    }

    @Test
    public void it_authenticates_a_user_with_valid_username_and_password()
    {
        when(users.identifiedBy(correctUsername)).thenReturn(registeredUser);

        assertThat(action.attemptLogin(correctUsername, correctPassword), is(true));
        assertThat(action.user(), is(registeredUser));
    }

    @Before
    public void configureAction() {
        correctUsername = "known";
        correctPassword = "correct password";
        registeredUser = User.registeredWith(
            1, correctUsername, Password.fromPlainText(correctPassword).toString()
        );
        users = mock(Users.class);
        action = new AuthenticateUser(users);
    }

    private Users users;
    private String correctUsername;
    private String correctPassword;
    private User registeredUser;
    private AuthenticateUser action;
}
