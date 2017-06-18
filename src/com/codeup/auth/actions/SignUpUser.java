/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.actions;

import com.codeup.auth.Password;
import com.codeup.auth.User;
import com.codeup.auth.Users;

public class SignUpUser {
    private Users users;

    public SignUpUser(Users users) {
        this.users = users;
    }

    public void signUp(String username, String password) {
        if (users.identifiedBy(username) != null) {
            throw DuplicateUser.with(username);
        }
        User user = User.signUp(username, Password.fromPlainText(password));
        users.add(user);
    }
}
