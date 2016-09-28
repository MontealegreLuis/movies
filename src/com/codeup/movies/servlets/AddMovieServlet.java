/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.servlets;

import com.codeup.movies.actions.AddMovie;
import com.codeup.movies.di.MoviesContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddMovieServlet", urlPatterns = {"/movies/new"})
public class AddMovieServlet extends HttpServlet {
    private AddMovie action;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            action = MoviesContainer.addMovie();
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize AddMovie action", e);
        }
    }

    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {

        action.add(
            request.getParameter("title"),
            Integer.parseInt(request.getParameter("rating")),
            request.getParameterValues("category[]")
        );

        response.sendRedirect("/movies");
    }

    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {

        request.setAttribute("categories", action.categories());

        request
            .getRequestDispatcher("/WEB-INF/movies/new.jsp")
            .forward(request, response)
        ;
    }
}
