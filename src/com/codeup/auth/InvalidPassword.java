/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

public class InvalidPassword extends IllegalArgumentException {
    private InvalidPassword(String format) {
        super(format);
    }

    public static InvalidPassword empty(String plainTextPassword) {
        return new InvalidPassword(String.format(
            "Password cannot be null or empty, '%s' found", plainTextPassword
        ));
    }

    public static InvalidPassword tooShort(int minLength, String plainTextPassword) {
        return new InvalidPassword(String.format(
            "Password should be at least %d characters long, '%s' found",
            minLength,
            plainTextPassword
        ));
    }
}
