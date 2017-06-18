/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class HashingAlgorithmTest {
    @Test
    public void it_hashes_plain_text()
    {
        String hash = algorithm.hash(plainTextPassword);

        assertThat(hash, containsString("$12$"));
        assertThat(hash.length(), is(60) );
    }

    @Test
    public void it_verifies_that_a_hash_matches_its_plain_text_version()
    {
        String hash = algorithm.hash(plainTextPassword);

        assertThat(algorithm.verify(plainTextPassword, hash), is(true));
    }

    private HashingAlgorithm algorithm = new HashingAlgorithm();
    private String plainTextPassword = "iLoveMyJ0b";
}