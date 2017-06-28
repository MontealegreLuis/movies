/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.infrastructure.web.servlets;

import com.codeup.auth.application.ServletLoginResponder;
import com.codeup.auth.domain.authentication.AuthenticateUser;
import com.codeup.auth.domain.authentication.CanAuthenticateUsers;
import com.codeup.auth.domain.authentication.Credentials;
import com.codeup.auth.domain.identity.User;
import com.codeup.auth.infrastructure.di.AuthContainer;
import com.codeup.auth.application.LoginInput;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet implements CanAuthenticateUsers {
    private AuthenticateUser action;
    private LoginInput input;
    private ServletLoginResponder responder;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        responder = new ServletLoginResponder(config.getInitParameter("homePage"));
        input = new LoginInput();
        try {
            action = AuthContainer.authenticateUser();
            action.attach(this);
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize AuthenticateUser action", e);
        }
    }

    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        responder.use(request, response);
        responder.respondToInputLoginCredentials();
    }

    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        responder.use(request, response);

        if (input.isValid(request.getParameter("username"), request.getParameter("password")))
            action.attemptLogin(Credentials.from(input.values()));
        else
            responder.respondToInvalidLoginInput(input);
    }

    @Override
    public void authenticationFailureUsing(Credentials credentials) {
        responder.respondToInvalidLoginAttemptWith(credentials);
    }

    @Override
    public void authenticationSucceededFor(User user) {
        responder.respondToASuccessfulAuthenticationOf(user);
    }
}
