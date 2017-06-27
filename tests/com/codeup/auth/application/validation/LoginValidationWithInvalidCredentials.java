/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.application.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class LoginValidationWithInvalidCredentials {
    @Test
    public void it_fails_validation_with_invalid_credentials() {
        validator.populateWith(input);

        assertThat(validator.isValid(), is(false));
        assertThat(validator.messages().size(), is(greaterThan(0)));
    }

    @Parameters(name = "credentials: username = {0}, password = {1}")
    public static Collection<Object[]> invalidCredentials() {
        return Arrays.asList(new Object[][]{
            {"luis", ""}, // empty password
            {"luis", "    "}, // blank password
            {"luis", null}, // null password
            {"luis", "short"}, // very short password (less than 8 characters long)
            {"", "changeme"}, // empty username
            {"    ", "changeme"}, // blank username
            {null, "changeme"}, // null username
            {"luis!montealegre ", "changeme"}  // invalid username (only ., -, and _)
        });
    }

    public LoginValidationWithInvalidCredentials(String username, String password) {
        input = new HashMap<>();
        input.put("username", username);
        input.put("password", password);
    }

    private Map<String, String> input;
    private LoginValidator validator = new LoginValidator();
}
