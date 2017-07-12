/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.servlets;

import com.codeup.movies.actions.RateMovie;
import com.codeup.movies.di.MoviesContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RateMovieServlet", urlPatterns = {"/movies/rate"})
public class RateMovieServlet extends HttpServlet {
    private RateMovie action;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            action = MoviesContainer.rateMovie();
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize RateMovie action", e);
        }
    }

    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {

        action.rate(
            Integer.parseInt(request.getParameter("id")),
            Integer.parseInt(request.getParameter("rating"))
        );

        response.sendRedirect("/movies");
    }
}
