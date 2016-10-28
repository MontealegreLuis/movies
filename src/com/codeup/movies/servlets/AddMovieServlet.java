/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.servlets;

import com.codeup.movies.actions.AddMovie;
import com.codeup.movies.di.MoviesContainer;
import com.codeup.movies.validation.AddMovieValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(name = "AddMovieServlet", urlPatterns = {"/movies/new"})
@MultipartConfig
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
        String thumbnail = saveThumbnail(request);

        AddMovieValidator validator = AddMovieValidator.withInput(
            request.getParameter("title"),
            request.getParameter("rating"),
            request.getParameterValues("category[]")
        );

        if (!validator.isValid()) {
            invalidInput(request, response, validator);
            return;
        }

        action.add(
            request.getParameter("title"),
            Integer.parseInt(request.getParameter("rating")),
            thumbnail,
            request.getParameterValues("category[]")
        );

        response.sendRedirect("/movies");
    }

    private void invalidInput(
        HttpServletRequest request,
        HttpServletResponse response,
        AddMovieValidator validator
    ) throws ServletException, IOException {
        request.setAttribute("errors", validator.messages());
        doGet(request, response);
    }

    private String saveThumbnail(
        HttpServletRequest request
    ) throws IOException, ServletException {
        Part part = request.getPart("thumbnail");

        return Upload.saveTo(
            uploadsFolder(),
            part.getSubmittedFileName(),
            part.getInputStream()
        );
    }

    private String uploadsFolder() {
        ServletContext context = getServletContext();
        return context.getRealPath(context.getInitParameter("thumbnails"));
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
