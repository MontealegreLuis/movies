/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    private String logoutPage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logoutPage = config.getInitParameter("logoutPage");
    }

    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
        response.sendRedirect(logoutPage);
    }
}
