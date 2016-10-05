/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

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

    public JdbcMovies(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Movie movie) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                Insert.into("movies").columns("title", "rating").toSQL(),
                Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, movie.title());
            statement.setInt(2, movie.rating());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            movie.setId(key.getInt(1));
            statement.close();
            if (movie.categories() != null) {
                categorize(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot add movie", e);
        }
    }

    private void categorize(Movie movie) throws SQLException {
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
            throw new RuntimeException("Cannot categorize movie", e);
        }
    }

    @Override
    public Movie with(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                Select.from("movies").where("id = ?").toSQL()
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return addCategoriesTo(new Movie(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getInt("rating")
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
            PreparedStatement statement = connection.prepareStatement(
                Update.table("movies").columns("title", "rating").where("id = ?").toSQL()
            );
            statement.setString(1, movie.title());
            statement.setInt(2, movie.rating());
            statement.setInt(3, movie.id());
            statement.executeUpdate();
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
