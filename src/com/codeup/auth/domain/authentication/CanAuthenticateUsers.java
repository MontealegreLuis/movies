/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.authentication;

import com.codeup.auth.domain.identity.User;

public interface CanAuthenticateUsers {
    void authenticationFailureUsing(Credentials credentials);
    void authenticationSucceededFor(User user);
}
