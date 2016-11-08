/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.RowMapper;
import com.codeup.movies.Category;

import java.util.List;

class CategoriesMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(List<Object> values) {
        return new Category(
            (long) values.get(0),
            values.get(1).toString()
        );
    }
}
