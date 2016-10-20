/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.Table;
import com.codeup.movies.Categories;
import com.codeup.movies.Category;
import com.codeup.movies.Movie;

import java.sql.*;

import java.util.List;

public class JdbcCategories implements Categories {
    private final Table<Category> table;

    public JdbcCategories(Connection connection) {
        table = new CategoriesTable(connection);
    }

    @Override
    public Category named(String name) {
        try {
            return table.select("*").where("name = ?").execute(name).fetch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category add(Category category) {
        try {
            return table.insert("name").execute(category.name()).fetch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Category> in(String... categories) {
        try {
            return table
                .select("*")
                .whereIn("id", categories)
                .execute(categories)
                .fetchAll()
            ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Category> all() {
        try {
            return table.select("*").execute().fetchAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Category> relatedTo(Movie movie) {
        try {
            return table
                .select("c.*")
                .addAlias("c")
                .join("movies_categories mc", "mc.category_id = c.id")
                .where("mc.movie_id = ?")
                .execute(movie.id())
                .fetchAll()
            ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
