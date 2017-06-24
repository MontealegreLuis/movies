/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.application;

import com.codeup.auth.application.validation.LoginValidator;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

public class LoginInputTest {
    @Test
    public void it_contains_error_messages_if_invalid_input_is_given()
    {
        input.populateWith(null, null);

        boolean valid = input.isValid();

        assertThat(valid, is(false));
        assertThat(input.messages().size(), greaterThan(0));
    }

    @Test
    public void it_does_not_have_error_messages_if_valid_input_is_given()
    {
        input.populateWith("luis.montealegre", "iL0veMyJob");

        boolean valid = input.isValid();

        assertThat(valid, is(true));
        assertThat(input.messages().size(), is(0));
    }

    @Test
    public void it_has_access_to_the_original_input()
    {
        String username = "luis.montealegre";
        String password = "iL0veMyJob";
        input.populateWith(username, password);

        assertThat(input.values().get("username"), is(username));
        assertThat(input.values().get("password"), is(password));
    }

    private LoginInput input = new LoginInput(new LoginValidator());
}