/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.authentication;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CredentialsTest {
    @Test
    public void it_has_access_to_the_provided_username_and_password()
    {
        String username = "luis.montealegre";
        String password = "iL0veMyJob";
        Map<String, String> input = new HashMap<>();
        input.put("username", username);
        input.put("password", password);

        Credentials credentials = Credentials.from(input);

        assertThat(credentials.username(), is(username));
        assertThat(credentials.password(), is(password));
    }
}
