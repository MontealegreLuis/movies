/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.movies.Categories;
import com.codeup.movies.Category;
import com.codeup.movies.Movie;

import java.sql.Connection;

import java.util.List;

public class JdbcCategories implements Categories {
    private final CategoriesTable table;

    public JdbcCategories(Connection connection) {
        table = new CategoriesTable(connection);
    }

    @Override
    public Category named(String name) {
        return table.findBy(name);
    }

    @Override
    public Category add(Category category) {
        return table.insert(category.name());
    }

    @Override
    public List<Category> in(String... categories) {
        return table.findAllIn(categories);
    }

    @Override
    public List<Category> all() {
        return table.findAll();
    }

    @Override
    public List<Category> relatedTo(Movie movie) {
        return table.findAllBy(movie.id());
    }
}
