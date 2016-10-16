/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.Query;
import com.codeup.db.Table;
import com.codeup.db.builders.queries.Insert;
import com.codeup.db.builders.queries.Select;
import com.codeup.db.builders.queries.Update;
import com.codeup.movies.*;

import java.sql.*;
import java.util.List;

public class JdbcMovies implements Movies {
    private Query<Movie> query;
    private Table<Movie> table;
    private Categories categories;

    public JdbcMovies(Connection connection) {
        query = new Query<>(connection, new MoviesMapper());
        table = new MoviesTable(connection);
        categories = new JdbcCategories(connection);
    }

    @Override
    public Movie add(Movie aMovie) {
        try {
            Movie movie = table
                .insert("title", "rating", "thumbnail")
                .fetch(aMovie.title(), aMovie.rating(), aMovie.thumbnail())
            ;
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
            Movie movie = table.select("*").where("id = ?").fetch(id);
            movie.addCategories(categories.relatedTo(movie));
            return movie;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Movie movie) {
        try {
            table
                .update("title", "rating")
                .where("id = ?")
                .execute(movie.title(), movie.rating(), movie.id())
            ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
