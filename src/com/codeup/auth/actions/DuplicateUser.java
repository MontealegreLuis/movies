/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.actions;

public class DuplicateUser extends RuntimeException {
    private DuplicateUser(String message) {
        super(message);
    }

    public static DuplicateUser with(String username) {
        return new DuplicateUser(
            String.format("The username %s is already in use by another user", username)
        );
    }
}
