/**
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
    private final String username;
    private final String password;
    private Map<String, List<String>> messages;

    private LoginValidator(String username, String password) {
        this.username = username;
        this.password = password;
        messages = new LinkedHashMap<>();
    }

    public static LoginValidator from(String username, String password) {
        return new LoginValidator(username, password);
    }

    @Override
    public boolean isValid() {
        validateUsername();
        validatePassword();

        return messages.size() == 0;
    }

    private void validatePassword() {
        ArrayList<String> messages = new ArrayList<>();
        if (GenericValidator.isBlankOrNull(password)) {
            messages.add("Enter your password");
        }
        if (messages.size() > 0) this.messages.put("password", messages);
    }

    private void validateUsername() {
        ArrayList<String> messages = new ArrayList<>();

        if (GenericValidator.isBlankOrNull(username)) {
            messages.add("Enter your username");
        }
        if (username != null && !GenericValidator.matchRegexp(username, "[A-Za-z0-9._-]+")) {
            messages.add("Your username does not have a valid format");
        }

        if (messages.size() > 0) this.messages.put("username", messages);
    }

    @Override
    public Map<String, List<String>> messages() {
        return messages;
    }
}
