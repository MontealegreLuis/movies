/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.application.authentication;

import com.codeup.auth.domain.authentication.Credentials;
import com.codeup.auth.domain.identity.User;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServletLoginResponderTest {

    @Test
    public void it_responds_to_input_login_credentials() throws Exception {
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        responder.respondToInputLoginCredentials();

        verify(request).getRequestDispatcher(anyString());
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void it_responds_to_invalid_login_input() throws Exception {
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        LoginInput input = new LoginInput();

        responder.respondToInvalidLoginInput(input);

        verify(request).setAttribute("username", null);
        verify(request).setAttribute("errors", input.messages());
        verify(request).getRequestDispatcher(anyString());
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void it_responds_to_a_successful_login() throws Exception
    {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        User registeredUser = User.registeredWith(1, "any", "any");

        responder.respondToASuccessfulAuthenticationOf(registeredUser);

        verify(session).setAttribute("user", registeredUser);
        verify(response).sendRedirect(homePage);
    }

    @Test
    public void it_responds_to_an_invalid_login_attempt() throws Exception
    {
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        Map<String, String> input = new HashMap<>();
        input.put("username", "unknown");
        input.put("password", "incorrect");
        Credentials invalidCredentials = Credentials.from(input);

        responder.respondToInvalidLoginAttemptWith(invalidCredentials);

        verify(request).setAttribute("username", invalidCredentials.username());
        verify(request).setAttribute(argThat(is("error")), anyString());
        verify(request).getRequestDispatcher(anyString());
        verify(dispatcher).forward(request, response);
    }


    @Before
    public void configureResponder() {
        homePage = "/home";
        responder = new ServletLoginResponder(homePage);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        responder.use(request, response);
    }

    private ServletLoginResponder responder;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String homePage;
}