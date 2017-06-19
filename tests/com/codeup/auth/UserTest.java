/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void it_can_be_signed_up()
    {
        assertThat(user.username(), is(username));
        assertThat(user.password().toString().length(), is(60)); // password is hashed
        assertThat(user.id(), is(0L)); // No ID yet
    }

    @Test
    public void it_verifies_its_password()
    {
        assertThat(user.passwordMatch(plainTextPassword), is(true));
    }

    @Before
    public void configureUser() {
        user = User.signUp(username, plainTextPassword);
    }

    private String username = "luis";
    private final String plainTextPassword = "iLoveMyJ0b";
    private User user;
}