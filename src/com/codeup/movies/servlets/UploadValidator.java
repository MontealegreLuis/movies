/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.servlets;

import com.codeup.validation.Validator;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UploadValidator implements Validator {
    private boolean onlyImages = false;
    private List<String> imageContentTypes;
    private boolean required = false;
    private final Map<String, List<String>> errors;
    private Part file;

    private UploadValidator(Part file) {
        this.file = file;
        errors = new HashMap<>();
    }

    static UploadValidator validate(Part file) {
        return new UploadValidator(file);
    }

    UploadValidator onlyImages() {
        onlyImages = true;
        setImageContentTypes();
        return this;
    }

    private void setImageContentTypes() {
        imageContentTypes = new ArrayList<>();
        imageContentTypes.add("image/png");
        imageContentTypes.add("image/jpeg");
        imageContentTypes.add("image/gif");
    }

    UploadValidator required() {
        required = true;
        return this;
    }

    @Override
    public boolean isValid() {
        List<String> messages = new ArrayList<>();
        if (required && file.getSize() == 0) {
            messages.add("Please add a thumbnail for this movie");
            errors.put("thumbnail", messages);
            return false;
        }

        if (onlyImages && !imageContentTypes.contains(file.getContentType())) {
            messages.add("Please upload a jpg, gif or png file");
            errors.put("thumbnail", messages);
            return false;
        }

        return errors.size() == 0;
    }

    @Override
    public Map<String, List<String>> messages() {
        return errors;
    }
}
