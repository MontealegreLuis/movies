/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.servlets;

import com.codeup.movies.actions.MoviesInformation;
import com.codeup.movies.actions.ViewMovies;
import com.codeup.movies.di.MoviesContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ViewMoviesServlet", urlPatterns = {"/movies"})
public class ViewMoviesServlet extends HttpServlet {
    private ViewMovies action;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            action = MoviesContainer.viewMovies();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {

        MoviesInformation information = action.view(
            request.getParameter("category")
        );

        request.setAttribute("categories", information.categories);
        request.setAttribute("movies", information.movies);
        request
            .getRequestDispatcher("/WEB-INF/movies/index.jsp")
            .forward(request, response)
        ;
    }
}
