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
import java.util.List;
import java.util.Map;

public class Upload implements Validator {
    private final String uploadsFolder;
    private final UploadValidator validator;
    private Part file;
    private String name;

    private Upload(Part file, String uploadsFolder) {
        this.file = file;
        this.uploadsFolder = uploadsFolder;
        validator = UploadValidator.validate(file);
    }

    static Upload fileTo(Part file, String uploadsFolder) {
        return new Upload(file, uploadsFolder);
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

    String name() {
        return name;
    }

    Upload onlyImages() {
        validator.onlyImages();
        return this;
    }

    Upload required() {
        validator.required();
        return this;
    }

    @Override
    public boolean isValid() {
        return validator.isValid();
    }

    @Override
    public Map<String, List<String>> messages() {
        return validator.messages();
    }
}
