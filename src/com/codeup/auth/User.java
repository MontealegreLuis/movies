/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

public class User {
    private final long id;
    private final String username;
    private final Password password;

    public User(long id, String username, Password password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static User signUp(String username, Password password) {
        return new User(0L, username, password);
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
