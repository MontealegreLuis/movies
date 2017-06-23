/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.identity;

public class User {
    private final long id;
    private final Username username;
    private final Password password;

    private User(long id, Username username, Password password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static User registeredWith(long id, String username, String password) {
        return new User(id, Username.from(username), Password.fromHash(password));
    }

    public static User signUp(String username, String plainTextPassword) {
        return new User(0L, Username.from(username), Password.fromPlainText(plainTextPassword));
    }

    public boolean passwordMatch(String plainTextPassword) {
        return password.verify(plainTextPassword);
    }

    public Username username() {
        return username;
    }

    public long id() {
        return id;
    }

    public Password password() {
        return password;
    }
}
