/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

public class User {
    private final long id;
    private final String username;
    private final Password password;

    private User(long id, String username, Password password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static User registeredWith(long id, String username, String password) {
        return new User(id, username, Password.fromHash(password));
    }

    public static User signUp(String username, String plainTextPassword) {
        return new User(0L, username, Password.fromPlainText(plainTextPassword));
    }

    public boolean passwordMatch(String plainTextPassword) {
        return password.verify(plainTextPassword);
    }

    public String username() {
        return username;
    }

    public long id() {
        return id;
    }

    public Password password() {
        return password;
    }
}
