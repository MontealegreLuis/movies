package com.codeup.auth.domain.identity;

import org.mindrot.jbcrypt.BCrypt;

public class Bcrypt implements HashingAlgorithm {
    @Override
    public String hash(String plainTextPassword) {
        final int rounds = 12;
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(rounds));
    }

    @Override
    public boolean verify(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}