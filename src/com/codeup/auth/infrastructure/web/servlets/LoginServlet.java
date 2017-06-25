/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.infrastructure.web.servlets;

import com.codeup.auth.application.LoginInput;
import com.codeup.auth.domain.authentication.AuthenticateUser;
import com.codeup.auth.domain.authentication.CanAuthenticateUsers;
import com.codeup.auth.domain.authentication.Credentials;
import com.codeup.auth.domain.identity.User;
import com.codeup.auth.infrastructure.di.AuthContainer;
import com.codeup.auth.application.validation.LoginValidator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet implements CanAuthenticateUsers {
    private AuthenticateUser action;
    private LoginInput input;
    private String homePage;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        homePage = config.getInitParameter("homePage");
        input = new LoginInput(new LoginValidator());
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
        request
            .getRequestDispatcher("/WEB-INF/auth/login.jsp")
            .forward(request, response)
        ;
    }

    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        input.populateWith(request.getParameter("username"), request.getParameter("password"));
        if (!input.isValid()) {
            invalidInput(input, request, response);
            return;
        }

        action.attemptLogin(Credentials.from(input.values()));
    }

    private void invalidInput(
        LoginInput input,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        request.setAttribute("username", input.values().get("username"));
        request.setAttribute("errors", input.messages());
        doGet(request, response);
    }

    @Override
    public void authenticationFailureUsing(Credentials credentials) {
        request.setAttribute("username", credentials.username());
        request.setAttribute(
            "error",
            "Either your username or password is incorrect."
        );
        try {
            doGet(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void authenticationSucceededFor(User user) {
        request.getSession().setAttribute("user", user);
        try {
            response.sendRedirect(homePage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
