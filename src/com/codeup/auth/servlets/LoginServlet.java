/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.servlets;

import com.codeup.auth.actions.AuthenticateUser;
import com.codeup.auth.di.AuthContainer;
import com.codeup.auth.validation.LoginValidator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private AuthenticateUser action;
    private String homePage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        homePage = config.getInitParameter("homePage");
        try {
            action = AuthContainer.authenticateUser();
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize AuthenticateUser action", e);
        }
    }

    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LoginValidator validator = LoginValidator.from(username, password);
        if (!validator.isValid()) {
            invalidInput(validator, request, response);
            return;
        }

        if (!action.attemptLogin(username, password )) {
            invalidCredentials(request, response);
            return;
        }

        request.getSession().setAttribute("user", action.user());
        response.sendRedirect(homePage);
    }

    private void invalidInput(
        LoginValidator validator,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        request.setAttribute("errors", validator.messages());
        doGet(request, response);
    }

    private void invalidCredentials(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        request.setAttribute(
            "error",
            "Either your username or password is incorrect."
        );
        doGet(request, response);
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
}
