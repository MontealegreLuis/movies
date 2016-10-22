/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

import java.sql.SQLException;

public interface RowMapper <T> {
    /**
     * Re-create an existing entity
     *
     * @param values
     * @return A existing entity
     * @throws SQLException
     */
    T mapRow(Object[] values);
}
