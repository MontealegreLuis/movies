/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.setup;

import com.codeup.movies.Category;
import com.codeup.movies.jdbc.JdbcCategories;

import java.sql.Connection;
import java.sql.SQLException;

class CategoriesSeeder {
    private final Connection connection;

    CategoriesSeeder(Connection connection) {
        this.connection = connection;
    }

    void seed() throws SQLException {
        String[] catalog = {
            "drama",
            "musical",
            "scifi",
            "horror",
            "comedy",
            "animated"
        };
        JdbcCategories categories = new JdbcCategories(connection);
        for (String category :catalog) {
            categories.add(Category.named(category));
        }
    }
}
