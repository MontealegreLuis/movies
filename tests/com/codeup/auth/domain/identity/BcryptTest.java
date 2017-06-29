/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.identity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class BcryptTest {
    @Test
    public void it_hashes_plain_text()
    {
        String hash = bcrypt.hash(plainTextPassword);

        assertThat(hash, containsString("$12$"));
        assertThat(hash.length(), is(60) );
    }

    @Test
    public void it_verifies_that_a_hash_matches_its_plain_text_version()
    {
        String hash = bcrypt.hash(plainTextPassword);

        assertThat(bcrypt.verify(plainTextPassword, hash), is(true));
    }

    private Bcrypt bcrypt = new Bcrypt();
    private String plainTextPassword = "iLoveMyJ0b";
}