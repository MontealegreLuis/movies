package com.codeup.auth;

import org.mindrot.jbcrypt.BCrypt;

public class HashingAlgorithm {
    public String hash(String plainTextPassword) {
        final int rounds = 12;
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(rounds));
    }

    public boolean verify(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}