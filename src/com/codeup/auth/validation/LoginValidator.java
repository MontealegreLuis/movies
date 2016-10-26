/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.validation;

import com.codeup.validation.Validator;
import org.apache.commons.validator.GenericValidator;

import java.util.LinkedHashMap;
import java.util.Map;

public class LoginValidator implements Validator {
    private final String username;
    private final String password;
    private Map<String, String[]> messages;

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
        boolean isValid = true;
        if (GenericValidator.isBlankOrNull(username)) {
            messages.put("username", new String[]{"Enter your username"});
            isValid = false;
        }
        if (GenericValidator.isBlankOrNull(password)) {
            messages.put("password", new String[]{"Enter your password"});
            isValid = false;
        }
        return isValid;
    }

    @Override
    public Map<String, String[]> messages() {
        return messages;
    }
}
