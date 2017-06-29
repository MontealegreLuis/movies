/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.identity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class PasswordTest {
    @Test
    public void it_hashes_a_plain_text_password()
    {
        assertThat(password.toString(), is(not(plainTextPassword)));
        assertThat(password.toString().length(), is(60));
    }

    @Test
    public void it_verifies_it_matches_the_correct_plain_text_password()
    {
        assertThat(password.verify(plainTextPassword), is(true));
    }

    @Test
    public void it_fails_to_verify_a_non_matching_plain_text_password()
    {
        assertThat(password.verify("i do not match"), is(false));
    }

    @Test
    public void it_creates_a_password_from_an_existing_hash()
    {
        Password anotherPassword = Password.fromHash(this.password.toString());

        assertThat(password.equals(anotherPassword), is(true)); // hashes should be the same
    }

    @Test
    public void it_cannot_be_null()
    {
        exception.expect(InvalidPassword.class);

        Password.fromPlainText(null);
    }

    @Test
    public void it_cannot_be_empty()
    {
        exception.expect(InvalidPassword.class);

        Password.fromPlainText("   ");
    }

    @Test
    public void it_cannot_be_shorter_than_eight_characters()
    {
        exception.expect(InvalidPassword.class);

        Password.fromPlainText("secure");
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private final String plainTextPassword = "iL0veMyJob";
    private final Password password = Password.fromPlainText(plainTextPassword);
}