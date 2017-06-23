/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.identity;

public class InvalidUsername extends IllegalArgumentException {

    private InvalidUsername(String format) {
        super(format);
    }

    public static InvalidUsername withEmpty(String value) {
        return new InvalidUsername(String.format("Username cannot be empty, found '%s'", value));
    }

    public static InvalidUsername with(String value) {
        return new InvalidUsername(String.format(
            "A username can only contain alphanumeric characters dots, dashes & underscores, %s found",
            value
        ));
    }
}
