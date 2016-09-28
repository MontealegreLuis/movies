/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.di;

import com.codeup.db.di.DbContainer;
import com.codeup.movies.Categories;
import com.codeup.movies.jdbc.JdbcCategories;
import com.codeup.movies.jdbc.JdbcMovies;
import com.codeup.movies.Movies;
import com.codeup.movies.actions.AddMovie;
import com.codeup.movies.actions.RateMovie;
import com.codeup.movies.actions.ViewMovie;
import com.codeup.movies.actions.ViewMovies;

import java.sql.SQLException;

public class MoviesContainer {
    private static AddMovie addMovie;
    private static RateMovie rateMovie;
    private static ViewMovie viewMovie;
    private static ViewMovies viewMovies;

    public static AddMovie addMovie() throws SQLException, ClassNotFoundException {
        if (addMovie == null) {
            addMovie = new AddMovie(categories(), movies());
        }
        return addMovie;
    }

    public static RateMovie rateMovie() throws SQLException, ClassNotFoundException {
        if (rateMovie == null) {
            rateMovie = new RateMovie(movies());
        }
        return rateMovie;
    }

    public static ViewMovie viewMovie() throws SQLException, ClassNotFoundException {
        if (viewMovie == null) {
            viewMovie = new ViewMovie(movies());
        }
        return viewMovie;
    }

    public static ViewMovies viewMovies() throws SQLException, ClassNotFoundException {
        if (viewMovies == null) {
            viewMovies = new ViewMovies(movies(), categories());
        }
        return viewMovies;
    }

    private static Movies movies() throws SQLException, ClassNotFoundException {
        return new JdbcMovies(DbContainer.connection());
    }

    private static Categories categories()
        throws SQLException, ClassNotFoundException {
        return new JdbcCategories(DbContainer.connection());
    }
}
