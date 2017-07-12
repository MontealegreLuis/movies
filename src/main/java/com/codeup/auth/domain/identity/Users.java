/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.identity;

public interface Users {
    User add(User user);
    User identifiedBy(String username);
}
