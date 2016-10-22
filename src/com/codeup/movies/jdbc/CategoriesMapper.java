/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.RowMapper;
import com.codeup.movies.Category;

class CategoriesMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(Object[] values) {
        return new Category(
            (long) values[0],
            values[1].toString()
        );
    }
}
