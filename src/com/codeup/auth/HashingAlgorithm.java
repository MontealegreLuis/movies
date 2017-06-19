/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth;

public interface HashingAlgorithm {
    String hash(String plainTextPassword);

    boolean verify(String plainTextPassword, String hashedPassword);
}
