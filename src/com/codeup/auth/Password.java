/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

public class Password {
    private String hash;
    private HashingAlgorithm hashingAlgorithm;

    private Password(String plainTextPassword) {
        this(plainTextPassword, new Bcrypt());
    }

    private Password(String password, HashingAlgorithm algorithm) {
        hash =  null != algorithm ? algorithm.hash(password): password;
        hashingAlgorithm = null != algorithm ? algorithm : new Bcrypt();
    }

    public static Password fromPlainText(String plainTextPassword) {
        return new Password(plainTextPassword);
    }

    public static Password fromHash(String hash) {
        return new Password(hash, null);
    }

    public boolean verify(String plainTextPassword) {
        return hashingAlgorithm.verify(plainTextPassword, hash);
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
