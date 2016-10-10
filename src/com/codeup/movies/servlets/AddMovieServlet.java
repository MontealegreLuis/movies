/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.servlets;

import com.codeup.movies.actions.AddMovie;
import com.codeup.movies.di.MoviesContainer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

        action.add(
            request.getParameter("title"),
            Integer.parseInt(request.getParameter("rating")),
            thumbnail,
            request.getParameterValues("category[]")
        );

        response.sendRedirect("/movies");
    }

    private String saveThumbnail(
        HttpServletRequest request
    ) throws IOException, ServletException {
        Part part = request.getPart("thumbnail");
        ServletContext context = getServletContext();
        String uploadsFolder = context.getRealPath(context.getInitParameter("thumbnails"));
        String name = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        try (InputStream content = part.getInputStream()) {
            File file = new File(new File(uploadsFolder), name);
            Files.copy(content, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return name;
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
