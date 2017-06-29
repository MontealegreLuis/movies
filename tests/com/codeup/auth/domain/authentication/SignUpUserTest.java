/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.domain.authentication;

import com.codeup.auth.domain.identity.User;
import com.codeup.auth.domain.identity.Users;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

    @Test
    public void it_prevents_the_creation_of_a_user_with_a_duplicate_name()
    {
        String username = "luis";
        when(users.identifiedBy(username))
            .thenReturn(User.signUp(username, "anything"))
        ;
        exception.expect(DuplicateUser.class);
        exception.expectMessage(Matchers.containsString(username));

        action.signUp(username, "iL0veMyJob");

        verify(users, never()).add(any(User.class));
    }

    @Before
    public void configureAction() {
        users = mock(Users.class);
        action = new SignUpUser(users);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private SignUpUser action;
    private Users users;
}
