/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.validation;

import com.codeup.movies.servlets.Upload;
import com.codeup.validation.Validator;
import org.apache.commons.validator.GenericValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMovieValidator implements Validator {
    private final String title;
    private final String rating;
    private final String[] categories;
    private final Upload upload;
    private final Map<String, List<String>> errors;

    private AddMovieValidator(
        String title,
        String rating,
        String[] categories,
        Upload upload
    ) {
        this.title = title;
        this.rating = rating;
        this.categories = categories;
        this.upload = upload;
        errors = new HashMap<>();
    }

    public static AddMovieValidator withInput(
        String title,
        String rating,
        String[] categories,
        Upload upload
    ) {
        return new AddMovieValidator(title, rating, categories, upload);
    }

    @Override
    public boolean isValid() {
        validateTitle();
        validateRating();
        validateCategories();
        validateUpload();
        return errors.size() == 0;
    }

    public String[] categories() {
        return categories == null ? new String[]{} : categories;
    }

    private void validateCategories() {
        List<String> messages = new ArrayList<>();
        if (categories().length == 0) {
            messages.add("Please select at least one category");
            errors.put("categories", messages);
        }
    }

    private void validateTitle() {
        List<String> messages = new ArrayList<>();

        if (GenericValidator.isBlankOrNull(title)) {
            messages.add("Please enter a title");
            errors.put("title", messages);
        }
    }

    private void validateRating() {
        List<String> messages = new ArrayList<>();

        if (!GenericValidator.isInt(rating)) {
            messages.add("Rating should be an integer number");
            errors.put("rating", messages);
            return;
        }

        if (!GenericValidator.isInRange(Integer.parseInt(rating), 1, 5)) {
            messages.add("Rating should be a number between 1 and 5");
            errors.put("rating", messages);
        }
    }

    private void validateUpload() {
        if (!upload.isValid()) {
            errors.putAll(upload.messages());
        }
    }

    @Override
    public Map<String, List<String>> messages() {
        return errors;
    }
}
