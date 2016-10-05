/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface RowMapper <T> {
    T mapRow(ResultSet resultSet) throws SQLException;

    Map<Integer, Object> mapColumns(T entity);

    Map<Integer, Object> mapIdentifier(T entity);
}
