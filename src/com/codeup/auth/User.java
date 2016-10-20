/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

public class User {
    private final long id;
    private final String username;
    private final String password;

    public User(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static User signUp(String username, String password) {
        return new User(0L, username, password);
    }

    public String password() {
        return password;
    }

    public String username() {
        return username;
    }

    public long id() {
        return id;
    }
}
