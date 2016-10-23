/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.RowMapper;
import com.codeup.db.Table;
import com.codeup.db.builders.queries.Insert;
import com.codeup.db.statements.SelectStatement;
import com.codeup.movies.Category;
import com.codeup.movies.Movie;
import com.codeup.movies.MoviesCriteria;
import com.codeup.pagination.Pagination;
import com.codeup.pagination.QueryPagination;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class MoviesTable extends Table<Movie> {
    MoviesTable(Connection connection) {
        super(connection);
    }

    Pagination<Movie> findAllMatching(MoviesCriteria criteria, int page) {
        SelectStatement<Movie> query = this
            .select("m.*")
            .addAlias("m")
            .matching(criteria)
        ;
        QueryPagination<Movie> storage = new QueryPagination<>(
            query, criteria.arguments().toArray()
        );
        return new Pagination<>(10, storage, page);
    }

    Movie findBy(int movieId) {
        try {
            return this.select("*").where("id = ?").execute(movieId).fetch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void update(String title, int rating, long id) {
        try {
            this
                .createUpdate("title", "rating")
                .where("id = ?")
                .execute(title, rating, id)
            ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Movie insert(
        String title,
        int rating,
        String thumbnail,
        List<Category> categories
    ) {
        try {
            Movie movie = this
                .createInsert("title", "rating", "thumbnail")
                .execute(title, rating, thumbnail)
                .fetch()
            ;
            movie.addCategories(categories);
            addCategoriesTo(movie);
            return movie;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addCategoriesTo(Movie movie) throws SQLException {
        for (Category category: movie.categories()) {
            this.executeUpdate(
                Insert.into("movies_categories").columns("movie_id", "category_id"),
                movie.id(),
                category.id()
            );
        }
    }

    @Override
    protected String table() {
        return "movies";
    }

    @Override
    protected RowMapper<Movie> mapper() {
        return new MoviesMapper();
    }
}
