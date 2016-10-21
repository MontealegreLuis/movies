/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.Table;
import com.codeup.db.builders.queries.Insert;
import com.codeup.db.statements.SelectStatement;
import com.codeup.movies.*;
import com.codeup.pagination.Pagination;
import com.codeup.pagination.QueryPagination;

import java.sql.*;

public class JdbcMovies implements Movies {
    private Table<Movie> table;
    private Categories categories;

    public JdbcMovies(Connection connection) {
        table = new MoviesTable(connection);
        categories = new JdbcCategories(connection);
    }

    @Override
    public Movie add(Movie aMovie) {
        try {
            Movie movie = table
                .insert("title", "rating", "thumbnail")
                .execute(aMovie.title(), aMovie.rating(), aMovie.thumbnail())
                .fetch()
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
            table.executeUpdate(
                Insert.into("movies_categories").columns("movie_id", "category_id"),
                movie.id(),
                category.id()
            );
        }
    }

    @Override
    public Movie with(int id) {
        try {
            Movie movie = table.select("*").where("id = ?").execute(id).fetch();
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
    public Pagination<Movie> matching(MoviesCriteria criteria, int page) {
        SelectStatement<Movie> query = table
            .select("m.*")
            .addAlias("m")
            .matching(criteria)
        ;
        QueryPagination<Movie> storage = new QueryPagination<>(
            query, criteria.arguments().toArray()
        );
        return new Pagination<>(10, storage, page);
    }
}
