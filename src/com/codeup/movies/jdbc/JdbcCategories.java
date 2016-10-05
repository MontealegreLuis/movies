/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.Query;
import com.codeup.db.builders.queries.Insert;
import com.codeup.db.builders.queries.Select;
import com.codeup.movies.Categories;
import com.codeup.movies.Category;

import java.sql.*;

import java.util.List;

public class JdbcCategories implements Categories {
    private final Query<Category> query;

    public JdbcCategories(Connection connection) {
        query = new Query<>(connection, new CategoriesMapper());
    }

    @Override
    public Category named(String name) {
        try {
            return query.selectOne(
                Select.from("categories").where("name = ?"),
                name
            );
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve category...", e);
        }
    }

    @Override
    public void add(Category category) {
        try {
            query.insert(
                Insert.into("categories").columns("name"),
                category
            );
        } catch (SQLException e) {
            throw new RuntimeException("Cannot add category", e);
        }
    }

    @Override
    public List<Category> with(String... categories) {
        try {
            return query.selectMany(
                Select
                    .from("categories")
                    .where("id", categories.length),
                categories
            );
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve the categories", e);
        }
    }

    @Override
    public List<Category> all() {
        try {
            return query.selectMany(Select.from("categories"));
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve the categories", e);
        }
    }
}
