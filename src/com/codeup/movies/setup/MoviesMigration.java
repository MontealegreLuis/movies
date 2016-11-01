package com.codeup.movies.setup;

import com.codeup.db.builders.schema.IntColumn;
import com.codeup.db.builders.schema.SchemaBuilder;
import com.codeup.db.builders.schema.Table;

import java.sql.Connection;
import java.sql.SQLException;

public class MoviesMigration {
    private Connection connection;

    public MoviesMigration(Connection connection) {
        this.connection = connection;
    }

    public void up() throws SQLException {
        SchemaBuilder schema = new SchemaBuilder(connection);

        usersTable(schema);
        moviesTable(schema);
        categoriesTable(schema);
        moviesCategoriesTable(schema);

        schema.build();
    }

    private void moviesCategoriesTable(SchemaBuilder schema) {
        Table moviesCategories = schema.table("movies_categories").ifNotExists();
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
    }

    private void categoriesTable(SchemaBuilder schema) {
        Table categories = schema.table("categories").ifNotExists();
        categories.increments("id");
        categories.string("name").makeRequired();
    }

    private void moviesTable(SchemaBuilder schema) {
        Table movies = schema.table("movies").ifNotExists();
        movies.increments("id");
        movies.string("title", 300).makeRequired();
        movies.integer("rating").defaultTo("0");
        movies.string("thumbnail");
    }

    private void usersTable(SchemaBuilder schema) {
        Table users = schema.table("users").ifNotExists();
        users.increments("id");
        users.string("username", 50).makeRequired();
        users.string("password").makeRequired();
    }
}
