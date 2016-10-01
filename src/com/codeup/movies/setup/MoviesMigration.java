package com.codeup.movies.setup;

import com.codeup.db.builders.schema.IntColumn;
import com.codeup.db.builders.schema.SchemaBuilder;
import com.codeup.db.builders.schema.Table;

import java.sql.Connection;
import java.sql.SQLException;

class MoviesMigration {
    private Connection connection;

    MoviesMigration(Connection connection) {
        this.connection = connection;
    }

    void up() throws SQLException {
        SchemaBuilder schema = new SchemaBuilder(connection);

        Table users = schema.table("users");
        users.increments("id");
        users.string("username", 50).makeRequired();
        users.string("password").makeRequired();

        Table movies = schema.table("movies");
        movies.increments("id");
        movies.string("title", 300).makeRequired();
        movies.integer("rating").defaultTo("0");

        Table categories = schema.table("categories");
        categories.increments("id");
        categories.string("name").makeRequired();

        Table moviesCategories = schema.table("movies_categories");
        IntColumn movieId = (IntColumn) moviesCategories
            .integer("movie_id")
            .unsigned()
            .makeRequired()
        ;
        IntColumn categoryId = (IntColumn) moviesCategories
            .integer("category_id")
            .unsigned()
            .makeRequired()
        ;
        moviesCategories.foreign(movieId).references("id").on("movies");
        moviesCategories.foreign(categoryId).references("id").on("categories");
        moviesCategories.primary(movieId, categoryId);

        schema.build();
    }
}
