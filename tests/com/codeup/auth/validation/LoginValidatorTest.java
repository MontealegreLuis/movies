/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.validation;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginValidatorTest {

    private LoginValidator validator;

    @Test
    public void it_validates_an_empty_password() {
        validator = LoginValidator.from("luis", "");

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("password"));
    }

    @Test
    public void it_validates_a_password_with_only_spaces() {
        validator = LoginValidator.from("luis", "    ");

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("password"));
    }

    @Test
    public void it_validates_a_null_password() {
        validator = LoginValidator.from("luis", null);

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("password"));
    }

    @Test
    public void it_validates_an_empty_username() {
        validator = LoginValidator.from("", "changeme");

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("username"));
    }

    @Test
    public void it_validates_a_username_with_only_spaces() {
        validator = LoginValidator.from("      ", "changeme");

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("username"));
    }

    @Test
    public void it_validates_a_null_username() {
        validator = LoginValidator.from(null, "changeme");

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("username"));
    }
}
