/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UsernameTest {
    @Test(expected = InvalidUsername.class)
    public void it_cannot_be_empty() {
        Username.from("    ");
    }

    @Test(expected = InvalidUsername.class)
    public void it_cannot_be_null() {
        Username.from(null);
    }

    @Test(expected = InvalidUsername.class)
    public void it_can_only_contain_alphanumeric_characters_dots_dashes_and_underscores()
    {
        Username.from("montealegre luis");
    }

    @Test
    public void it_can_be_compared_to_another_username()
    {
        String validHandle = "montealegre.luis";
        Username username = Username.from(validHandle);

        assertThat(username.equals(Username.from(validHandle)), is(true));
    }
    
    @Test
    public void it_gets_its_current_value()
    {
        String validHandle = "montealegre_123";
        Username username = Username.from(validHandle);

        assertThat(username.value().equals(validHandle), is(true));
    }
    
}