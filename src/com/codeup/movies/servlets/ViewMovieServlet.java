/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.servlets;

import com.codeup.movies.actions.ViewMovie;
import com.codeup.movies.di.MoviesContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ViewMovieServlet", urlPatterns = { "/movies/show" })
public class ViewMovieServlet extends HttpServlet {
    private ViewMovie action;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            action = MoviesContainer.viewMovie();
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize ViewMovie action.", e);
        }
    }

    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {

        request.setAttribute(
            "movie",
            action.view(Integer.parseInt(request.getParameter("id")))
        );

        request
            .getRequestDispatcher("/WEB-INF/movies/show.jsp")
            .forward(request, response)
        ;
    }
}
