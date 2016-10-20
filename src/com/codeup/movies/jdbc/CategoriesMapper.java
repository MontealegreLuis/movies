/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.RowMapper;
import com.codeup.movies.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

class CategoriesMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet resultSet) throws SQLException {
        return new Category(resultSet.getLong("id"), resultSet.getString("name"));
    }

    @Override
    public Category mapRow(Object[] values) {
        return new Category(
            (long) values[0],
            values[1].toString()
        );
    }

    @Override
    public Category newEntity(long id, Object[] parameters) {
        return new Category(id, parameters[0].toString());
    }
}
