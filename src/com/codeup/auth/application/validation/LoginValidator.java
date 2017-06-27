/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.application.validation;

import com.codeup.validation.Validator;
import org.apache.commons.validator.GenericValidator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LoginValidator implements Validator {
    private Map<String, String> input;
    private Map<String, List<String>> messages;

    public LoginValidator() {
        messages = new LinkedHashMap<>();
    }

    public void populateWith(Map<String, String> input) {
        this.input = input;
    }

    @Override
    public boolean isValid() {
        validateUsername(input.get("username"));
        validatePassword(input.get("password"));

        return messages.size() == 0;
    }

    private void validatePassword(String password) {
        ArrayList<String> messages = new ArrayList<>();

        if (GenericValidator.isBlankOrNull(password)) messages.add("Enter your password");
        if (password != null && password.length() < 8) messages.add("Password must be at least 8 characters long");

        if (messages.size() > 0) this.messages.put("password", messages);
    }

    private void validateUsername(String username) {
        ArrayList<String> messages = new ArrayList<>();

        if (GenericValidator.isBlankOrNull(username)) messages.add("Enter your username");
        if (username != null && !GenericValidator.matchRegexp(username, "[A-Za-z0-9._-]+"))
            messages.add("Your username does not have a valid format");

        if (messages.size() > 0) this.messages.put("username", messages);
    }

    @Override
    public Map<String, List<String>> messages() {
        return messages;
    }
}
