/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.Query;
import com.codeup.db.builders.queries.Insert;
import com.codeup.db.builders.queries.Select;
import com.codeup.db.builders.queries.Update;
import com.codeup.movies.Category;
import com.codeup.movies.Movie;
import com.codeup.movies.Movies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcMovies implements Movies {
    private Connection connection;
    private Query<Movie> query;

    public JdbcMovies(Connection connection) {
        this.connection = connection;
        query = new Query<>(connection, new MoviesMapper());
    }

    @Override
    public Movie add(Movie movie) {
        try {
            int id = query.insert(
                Insert.into("movies").columns("title", "rating"),
                movie
            );
            Movie newMovie = new Movie(id, movie.title(), movie.rating());
            if (movie.isCategorized()) {
                newMovie.addCategories(movie.categories());
                addCategories(newMovie);
            }
            return newMovie;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addCategories(Movie movie) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
            Insert.into("movies_categories").columns("movie_id", "category_id").toSQL()
        );
        movie.categories().forEach(category -> attach(movie, category, statement));
        statement.close();
    }

    private void attach(Movie movie, Category category, PreparedStatement statement) {
        try {
            statement.setInt(1, movie.id());
            statement.setInt(2, category.id());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot addCategories movie", e);
        }
    }

    @Override
    public Movie with(int id) {
        try {
            return addCategoriesTo(query.selectOne(
                Select.from("movies").where("id = ?"),
                id
            ));
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find movie", e);
        }
    }

    private Movie addCategoriesTo(Movie movie) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                Select
                    .from("categories c")
                    .columns("c.*")
                    .join("movies_categories mc", "mc.category_id = c.id")
                    .where("mc.movie_id = ?")
                    .toSQL()
            );
            statement.setInt(1, movie.id());
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Category> categories = new ArrayList<>();
            while (resultSet.next()) {
                categories.add(new Category(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
                ));
            }
            movie.addCategories(categories);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot add categories to movie", e);
        }
        return movie;
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
    public List<Movie> all() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                Select.from("movies").toSQL()
            );
            return populateMovies(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve the movies", e);
        }
    }

    @Override
    public List<Movie> inCategory(String category) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                Select
                    .from("movies m")
                    .columns("m.*")
                    .join("movies_categories mc", "mc.movie_id = m.id")
                    .join("categories c", "c.id = mc.category_id")
                    .where("c.id = ?")
                    .toSQL()
            );
            statement.setString(1, category);

            return populateMovies(statement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException("Cannot filter movies by category", e);
        }
    }

    private ArrayList<Movie> populateMovies(ResultSet resultSet) throws SQLException {
        ArrayList<Movie> movies = new ArrayList<>();
        while (resultSet.next()) {
            movies.add(new Movie(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getInt("rating")
            ));
        }
        return movies;
    }
}
