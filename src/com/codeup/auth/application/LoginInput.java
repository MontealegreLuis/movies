/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.application;

import org.apache.commons.validator.GenericValidator;

import java.util.*;

public class LoginInput {
    private Map<String, List<String>> messages;
    private Map<String, String> values;

    public LoginInput() {
        messages = new LinkedHashMap<>();
        values = new HashMap<>();
    }

    public Map<String, List<String>> messages() {
        return messages;
    }

    public Map<String, String> values() {
        return values;
    }

    public boolean isValid(String username, String password) {
        values.put("username", username);
        values.put("password", password);

        validateUsername(username);
        validatePassword(password);

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
}
