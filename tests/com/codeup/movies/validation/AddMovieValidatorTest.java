/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.validation;

import org.junit.Test;

import static org.junit.Assert.*;

public class AddMovieValidatorTest {

    private AddMovieValidator validator;

    @Test
    public void it_fails_validation_if_empty_title_is_given() {
        validator = AddMovieValidator.withInput("  ", "5", new String[]{"2"});

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("title"));
    }

    @Test
    public void it_fails_validation_if_empty_rating_is_given() {
        validator = AddMovieValidator.withInput("The Matrix", null, new String[]{"2"});

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("rating"));
    }

    @Test
    public void it_fails_validation_if_rating_is_not_numeric() {
        validator = AddMovieValidator.withInput("The Matrix", "Five", new String[]{"2"});

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("rating"));
    }

    @Test
    public void it_fails_validation_if_rating_is_not_between_1_and_5() {
        validator = AddMovieValidator.withInput("The Matrix", "8", new String[]{"2"});

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("rating"));
    }

    @Test
    public void it_fails_validation_if_no_category_is_given() {
        validator = AddMovieValidator.withInput("The Matrix", "3", null);

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("categories"));
    }
}
