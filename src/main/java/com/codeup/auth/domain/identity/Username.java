/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.identity;

public class Username {
    private final String value;

    public static Username from(String value) {
        return new Username(value);
    }

    public String value() {
        return value;
    }

    private Username(String value) {
        assertNotEmpty(value);
        assertRegex(value);
        this.value = value;
    }

    private void assertRegex(String value) {
        if (!value.matches("^[A-Za-z0-9._-]+$")) throw InvalidUsername.with(value);
    }

    private void assertNotEmpty(String value) {
        if (value == null || value.trim().isEmpty()) throw InvalidUsername.withEmpty(value);
    }

    @Override
    public boolean equals(Object anotherUsername) {
        return anotherUsername != null
            && anotherUsername instanceof Username
            && ((Username) anotherUsername).value.equals(this.value);
    }
}
