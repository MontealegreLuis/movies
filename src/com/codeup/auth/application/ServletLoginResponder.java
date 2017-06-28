/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.application;

import com.codeup.auth.application.validation.LoginValidator;
import com.codeup.auth.domain.authentication.Credentials;
import com.codeup.auth.domain.identity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletLoginResponder implements LoginResponder {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String homePage;
    interface ServletOperation {
        void execute() throws IOException, ServletException;
    }

    public ServletLoginResponder(String homePage) {
        this.homePage = homePage;
    }

    public void use(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public void respondToInputLoginCredentials() {
        tryOperation(() ->
            request.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response)
        );
    }

    @Override
    public void respondToInvalidLoginInput(LoginValidator input) {
        request.setAttribute("username", input.values().get("username"));
        request.setAttribute("errors", input.messages());
        respondToInputLoginCredentials();
    }

    @Override
    public void respondToASuccessfulAuthenticationOf(User user) {
        tryOperation(() -> {
            request.getSession().setAttribute("user", user);
            response.sendRedirect(homePage);
        });
    }

    @Override
    public void respondToInvalidLoginAttemptWith(Credentials credentials) {
        tryOperation(() -> {
            request.setAttribute("username", credentials.username());
            request.setAttribute(
                "error",
                "Either your username or password is incorrect."
            );
            request
                .getRequestDispatcher("/WEB-INF/auth/login.jsp")
                .forward(request, response)
            ;
        });
    }

    private void tryOperation(ServletOperation operation) {
        try {
            operation.execute();
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
