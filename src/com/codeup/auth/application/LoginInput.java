/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.application;

import com.codeup.auth.application.validation.LoginValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginInput {
    private LoginValidator validator;
    private Map<String, String> input;

    public LoginInput(LoginValidator validator) {
        this.validator = validator;
    }

    public void populateWith(String username, String password) {
        input = new HashMap<>();
        input.put("username", username);
        input.put("password", password);
        validator.populateWith(input);
    }

    public Map<String, String> values() {
        return input;
    }

    public boolean isValid() {
        return validator.isValid();
    }

    public Map<String, List<String>> messages() {
        return validator.messages();
    }
}
