/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.Query;
import com.codeup.db.builders.queries.Insert;
import com.codeup.db.builders.queries.Select;
import com.codeup.db.builders.queries.Update;
import com.codeup.movies.*;

import java.sql.*;
import java.util.List;

public class JdbcMovies implements Movies {
    private Query<Movie> query;
    private Categories categories;

    public JdbcMovies(Connection connection) {
        query = new Query<>(connection, new MoviesMapper());
        categories = new JdbcCategories(connection);
    }

    @Override
    public Movie add(Movie aMovie) {
        try {
            int id = query.insert(
                Insert.into("movies").columns("title", "rating", "thumbnail"),
                aMovie
            );
            Movie movie = new Movie(
                id, aMovie.title(), aMovie.rating(), aMovie.thumbnail()
            );
            movie.addCategories(aMovie.categories());
            addCategoriesTo(movie);
            return movie;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addCategoriesTo(Movie movie) throws SQLException {
        for (Category category: movie.categories()) {
            query.insert(
                Insert.into("movies_categories").columns("movie_id", "category_id"),
                statement -> {
                    statement.setInt(1, movie.id());
                    statement.setInt(2, category.id());
                }
            );
        }
    }

    @Override
    public Movie with(int id) {
        try {
            Movie movie = query.selectOne(Select.from("movies").where("id = ?"), id);
            movie.addCategories(categories.relatedTo(movie));
            return movie;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find movie", e);
        }
    }

    @Override
    public void update(Movie movie) {
        try {
            query.update(
                Update.table("movies").columns("title", "rating").where("id = ?"),
                movie
            );
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update movie", e);
        }
    }

    @Override
    public List<Movie> matching(MoviesCriteria criteria) {
        Select select = Select.from("movies m").columns("m.*");
        criteria.applyTo(select);
        try {
            return query.selectMany(select, criteria.arguments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
