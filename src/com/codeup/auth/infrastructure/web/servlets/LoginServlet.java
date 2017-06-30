/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.infrastructure.web.servlets;

import com.codeup.auth.application.authentication.LoginAction;
import com.codeup.auth.application.authentication.ServletLoginResponder;
import com.codeup.auth.infrastructure.di.AuthContainer;
import com.codeup.auth.application.authentication.LoginInput;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private LoginAction command;
    private ServletLoginResponder responder;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        responder = new ServletLoginResponder(config.getInitParameter("homePage"));
        try {
            command = new LoginAction(responder, new LoginInput(), AuthContainer.authenticateUser());
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize AuthenticateUser action", e);
        }
    }

    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        responder.use(request, response);
        command.inputLoginCredentials();
    }

    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        responder.use(request, response);
        command.attemptLogin(request.getParameter("username"), request.getParameter("password"));
    }
}
