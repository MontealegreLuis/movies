package com.codeup.movies.servlets;
/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */

import org.junit.Test;

import javax.servlet.http.Part;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UploadValidatorTest {
    private UploadValidator validator;

    @Test
    public void it_fails_validation_when_required_file_is_not_present() {
        Part file = mock(Part.class);
        when(file.getSize()).thenReturn(0L);

        validator = UploadValidator.validate(file).required();

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("thumbnail"));
    }

    @Test
    public void it_passes_validation_when_required_file_is_present() {
        Part file = mock(Part.class);
        when(file.getSize()).thenReturn(100L);

        validator = UploadValidator.validate(file).required();

        assertTrue(validator.isValid());
        assertFalse(validator.messages().containsKey("thumbnail"));
    }

    @Test
    public void it_fails_validation_when_image_file_has_invalid_content_type() {
        Part file = mock(Part.class);
        when(file.getContentType()).thenReturn("text/plain");

        validator = UploadValidator.validate(file).onlyImages();

        assertFalse(validator.isValid());
        assertTrue(validator.messages().containsKey("thumbnail"));
    }

    @Test
    public void it_passes_validation_when_valid_image_is_provided() {
        Part file = mock(Part.class);
        when(file.getContentType()).thenReturn("image/jpeg");

        validator = UploadValidator.validate(file).onlyImages();

        assertTrue(validator.isValid());
        assertFalse(validator.messages().containsKey("thumbnail"));
    }
}
