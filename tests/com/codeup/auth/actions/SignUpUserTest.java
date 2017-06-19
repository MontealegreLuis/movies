/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.actions;

import com.codeup.auth.User;
import com.codeup.auth.Users;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SignUpUserTest {

    @Test
    public void it_signs_up_a_new_user()
    {
        when(users.identifiedBy("luis")).thenReturn(null);

        action.signUp("luis", "iL0veMyJob");

        verify(users).add(any(User.class));
    }

    @Test(expected = DuplicateUser.class)
    public void it_prevents_the_creation_of_a_user_with_a_duplicate_name()
    {
        when(users.identifiedBy("luis"))
            .thenReturn(User.signUp("luis", "anything"))
        ;

        action.signUp("luis", "iL0veMyJob");

        verify(users, never()).add(any(User.class));
    }


    @Before
    public void configureAction() {
        users = mock(Users.class);
        action = new SignUpUser(users);
    }

    private SignUpUser action;
    private Users users;
}
