/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

import org.mindrot.jbcrypt.BCrypt;

public class Password {
    public static String hash(String rawPassword) {
        int rounds = 12;
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(rounds));
    }

    public static boolean verify(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
