/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.movies.*;
import com.codeup.pagination.Pagination;

import java.sql.Connection;

public class JdbcMovies implements Movies {
    private MoviesTable table;
    private Categories categories;

    public JdbcMovies(Connection connection) {
        table = new MoviesTable(connection);
        categories = new JdbcCategories(connection);
    }

    @Override
    public Movie add(Movie movie) {
        return table.insert(
            movie.title(),
            movie.rating(),
            movie.thumbnail(),
            movie.categories()
        );
    }

    @Override
    public Movie with(int id) {
        Movie movie = table.findBy(id);

        if (movie == null) return null;

        movie.addCategories(categories.relatedTo(movie));
        return movie;
    }

    @Override
    public void update(Movie movie) {
        table.update(movie.title(), movie.rating(), movie.id());
    }

    @Override
    public Pagination<Movie> matching(MoviesCriteria criteria, int page) {
        return table.findAllMatching(criteria, page);
    }
}
