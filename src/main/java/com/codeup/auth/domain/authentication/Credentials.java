/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.authentication;

import java.util.Map;

public class Credentials {
    private String username;
    private String password;

    private Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Credentials from(Map<String, String> input) {
        return new Credentials(input.get("username"), input.get("password"));
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }
}
