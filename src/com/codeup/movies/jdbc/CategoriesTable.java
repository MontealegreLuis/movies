/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.RowMapper;
import com.codeup.db.Table;
import com.codeup.movies.Category;

import java.sql.Connection;

class CategoriesTable extends Table<Category> {
    CategoriesTable(Connection connection) {
        super(connection);
    }

    @Override
    protected String table() {
        return "categories c";
    }

    @Override
    protected RowMapper<Category> mapper() {
        return new CategoriesMapper();
    }
}
