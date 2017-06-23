/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.application.validation;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginValidatorTest {

    private LoginValidator validator;

    @Test
    public void it_fails_validation_with_an_empty_password() {
        validator = LoginValidator.from("luis", "");

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("password"));
    }

    @Test
    public void it_fails_validation_with_a_password_with_only_spaces() {
        validator = LoginValidator.from("luis", "    ");

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("password"));
    }

    @Test
    public void it_fails_validation_with_a_null_password() {
        validator = LoginValidator.from("luis", null);

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("password"));
    }

    @Test
    public void it_fails_validation_with_an_empty_username() {
        validator = LoginValidator.from("", "changeme");

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("username"));
    }

    @Test
    public void it_fails_validation_with_a_username_with_only_spaces() {
        validator = LoginValidator.from("      ", "changeme");

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("username"));
    }

    @Test
    public void it_fails_validation_with_a_null_username() {
        validator = LoginValidator.from(null, "changeme");

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("username"));
    }

    @Test
    public void it_fails_validation_with_a_username_with_invalid_format() {
        validator = LoginValidator.from("luis!montealegre ", "changeme");

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("username"));
    }

    @Test
    public void it_passes_if_valid_credentials_are_provided() {
        validator = LoginValidator.from("Luis.Mont-al3gr_ ", "changeme");

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("username"));
    }
}
