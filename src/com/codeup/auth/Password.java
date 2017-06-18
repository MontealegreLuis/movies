/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

public class Password {
    private String hash;
    private HashingAlgorithm bcrypt;

    private Password(String plainTextPassword) {
        this(plainTextPassword, new HashingAlgorithm());
    }

    private Password(String password, HashingAlgorithm algorithm) {
        hash =  null != algorithm ? algorithm.hash(password): password;
        bcrypt = null != algorithm ? algorithm : new HashingAlgorithm();
    }

    public static Password fromPlainText(String plainTextPassword) {
        return new Password(plainTextPassword);
    }

    public static Password fromHash(String hash) {
        return new Password(hash, null);
    }

    public boolean verify(String plainTextPassword) {
        return bcrypt.verify(plainTextPassword, hash);
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
