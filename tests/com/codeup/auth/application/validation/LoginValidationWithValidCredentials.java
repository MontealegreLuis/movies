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
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class LoginValidationWithValidCredentials {
    @Test
    public void it_passes_validation_if_valid_credentials_are_provided() {
        validator.populateWith(input);

        assertThat(validator.isValid(), is(true));
        assertThat(validator.messages().size(), is(0));
    }

    @Parameters(name = "credentials: username = {0}, password = {1}")
    public static Collection<Object[]> invalidCredentials() {
        return Arrays.asList(new Object[][]{
            {"Luis.Montealegre", "changeme"},
            {"Luis.Mont-alegre", "changeme"},
            {"Luis.Mont-al3gre", "changeme"},
            {"Luis.Mont-al3gr_", "changeme"},
            {"Luis.Mont-al3gr_", "very-Long_password!"},
        });
    }

    public LoginValidationWithValidCredentials(String username, String password) {
        input = new HashMap<>();
        input.put("username", username);
        input.put("password", password);
    }

    private Map<String, String> input;
    private LoginValidator validator = new LoginValidator();
}
