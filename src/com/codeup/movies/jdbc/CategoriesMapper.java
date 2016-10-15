/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.RowMapper;
import com.codeup.movies.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CategoriesMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet resultSet) throws SQLException {
        return new Category(resultSet.getInt("id"), resultSet.getString("name"));
    }

    @Override
    public Category newEntity(int id, Object[] parameters) {
        return new Category(id, parameters[0].toString());
    }

    @Override
    public Map<Integer, Object> mapColumns(Category category) {
        Map<Integer, Object> mapping = new LinkedHashMap<>();
        mapping.put(1, category.name());
        return mapping;
    }

    @Override
    public Map<Integer, Object> mapIdentifier(Category category) {
        Map<Integer, Object> mapping = new LinkedHashMap<>();
        mapping.put(2, category.id());
        return mapping;
    }
}
