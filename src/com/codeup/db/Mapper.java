/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class Mapper {
    public static void map(
        PreparedStatement statement,
        Object[] parameters
    ) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
    }

    public static void map(
        PreparedStatement statement,
        Map<Integer, Object> columns
    ) {
        columns.forEach((k, v) -> {
            try {
                statement.setObject(k, v);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
