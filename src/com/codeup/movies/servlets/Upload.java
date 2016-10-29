/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.servlets;

import com.codeup.validation.Validator;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Upload implements Validator {
    private final String uploadsFolder;
    private boolean onlyImages = false;
    private List<String> imageContentTypes;
    private boolean required = false;
    private final Map<String, List<String>> errors;
    private Part file;
    private String name;

    private Upload(Part file, String uploadsFolder) {
        this.file = file;
        this.uploadsFolder = uploadsFolder;
        errors = new HashMap<>();
    }

    static Upload fileTo(Part file, String uploadsFolder) {
        return new Upload(file, uploadsFolder);
    }

    String name() {
        return name;
    }

    Upload onlyImages() {
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

    Upload required() {
        required = true;
        return this;
    }

    void move() throws IOException {
        save(file.getSubmittedFileName(), file.getInputStream());
    }

    private void save(
        String name,
        InputStream content
    ) throws IOException {
        this.name = Paths.get(name).getFileName().toString();
        File file = new File(new File(uploadsFolder), this.name);
        Files.copy(content, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
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
