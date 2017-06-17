/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.servlets;

import com.codeup.auth.actions.SignUpUser;
import com.codeup.auth.di.AuthContainer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignUpServlet", urlPatterns = "/sign-up")
public class SignUpServlet extends HttpServlet {
    private SignUpUser action;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            action = AuthContainer.signupUser();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/auth/sign-up.jsp").forward(request, response);
    }

    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        action.signUp(request.getParameter("username"), request.getParameter("password"));
        response.sendRedirect("/login");
    }
}
