/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

public class Password {
    private String hash;
    private HashingAlgorithm hashingAlgorithm;

    public static Password fromPlainText(String plainTextPassword) {
        return new Password(plainTextPassword);
    }

    public static Password fromHash(String hash) {
        return new Password(hash, null);
    }

    public boolean verify(String plainTextPassword) {
        return hashingAlgorithm.verify(plainTextPassword, hash);
    }

    private Password(String plainTextPassword) {
        this(plainTextPassword, new Bcrypt());
    }

    private Password(String password, HashingAlgorithm algorithm) {
        hash =  null != algorithm ? hashPassword(password, algorithm) : password;
        hashingAlgorithm = null != algorithm ? algorithm : new Bcrypt();
    }

    private String hashPassword(String plainTextPassword, HashingAlgorithm algorithm) {
        assertNotEmpty(plainTextPassword);
        assertLength(plainTextPassword);
        return algorithm.hash(plainTextPassword);
    }

    private void assertLength(String plainTextPassword) {
        if (plainTextPassword.trim().length() < 8)
            throw InvalidPassword.tooShort(8, plainTextPassword);
    }

    private void assertNotEmpty(String plainTextPassword) {
        if (plainTextPassword == null || plainTextPassword.trim().isEmpty())
            throw InvalidPassword.empty(plainTextPassword);
    }

    @Override
    public String toString() {
        return hash;
    }

    @Override
    public boolean equals(Object anotherPassword) {
        return anotherPassword != null
            && anotherPassword instanceof Password
            && ((Password) anotherPassword).hash.equals(hash);
    }
}
