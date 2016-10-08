/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ParametersMapper {
    void map(PreparedStatement statement) throws SQLException;
}
