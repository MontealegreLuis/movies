/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

public class User {
    private final int id;
    private final String username;
    private final String password;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static User signUp(String username, String password) {
        return new User(0, username, password);
    }

    public String password() {
        return password;
    }

    public String username() {
        return username;
    }

    public int id() {
        return id;
    }
}
