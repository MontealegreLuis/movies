/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.application.authentication;

import com.codeup.auth.domain.authentication.Credentials;
import com.codeup.auth.domain.identity.User;

public interface LoginResponder {
    void respondToInputLoginCredentials();
    void respondToInvalidLoginInput(LoginInput input);
    void respondToASuccessfulAuthenticationOf(User user);
    void respondToInvalidLoginAttemptWith(Credentials credentials);
}
