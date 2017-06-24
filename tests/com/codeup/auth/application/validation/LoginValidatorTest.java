/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.application.validation;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class LoginValidatorTest {
    @Test
    public void it_fails_validation_with_an_empty_password() {
        validator.populateWith(input("luis", ""));

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("password"));
    }

    @Test
    public void it_fails_validation_with_a_password_with_only_spaces() {
        validator.populateWith(input("luis", "    "));

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("password"));
    }

    @Test
    public void it_fails_validation_with_a_null_password() {
        validator.populateWith(input("luis", null));

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("password"));
    }

    @Test
    public void it_fails_validation_with_an_empty_username() {
        validator.populateWith(input("", "changeme"));

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("username"));
    }

    @Test
    public void it_fails_validation_with_a_username_with_only_spaces() {
        validator.populateWith(input("      ", "changeme"));

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("username"));
    }

    @Test
    public void it_fails_validation_with_a_null_username() {
        validator.populateWith(input(null, "changeme"));

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("username"));
    }

    @Test
    public void it_fails_validation_with_a_username_with_invalid_format() {
        validator.populateWith(input("luis!montealegre ", "changeme"));

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("username"));
    }

    @Test
    public void it_passes_if_valid_credentials_are_provided() {
        validator.populateWith(input("Luis.Mont-al3gr_ ", "changeme"));

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("username"));
    }

    private Map<String, String> input(String username, String password) {
        Map<String, String> input = new HashMap<>();
        input.put("username", username);
        input.put("password", password);
        return input;
    }

    private LoginValidator validator = new LoginValidator();
}
