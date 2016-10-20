/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.statements;

import com.codeup.db.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hydrator<T> {
    private Object[][] values;
    private RowMapper<T> mapper;

    private Hydrator(Object[][] values, RowMapper<T> mapper) {
        this.values = values;
        this.mapper = mapper;
    }

    public Hydrator(ResultSet resultSet, RowMapper<T> mapper) throws SQLException {
        this(Hydrator.populateValues(resultSet), mapper);
    }

    public Hydrator(long id, Object[] insertedValues, RowMapper<T> mapper) {
        this.mapper = mapper;
        values = new Object[1][insertedValues.length + 1];
        System.arraycopy(new Object[]{id}, 0, values[0], 0, 1);
        System.arraycopy(insertedValues, 0, values[0], 1, insertedValues.length);
    }

    public T fetch() {
        if (values.length == 0) return null;

        return mapper.mapRow(values[0]);
    }

    public List<T> fetchAll() {
        List<T> entities = new ArrayList<>();
        for (Object[] value : values) {
            entities.add(mapper.mapRow(value));
        }
        return entities;
    }

    private static Object[][] populateValues(
        ResultSet resultSet
    ) throws SQLException {
        List<Object[]> rows = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();

        while (resultSet.next()) {
            Object[] row = new Object[columnCount];
            for (int j = 1; j <= columnCount; j++) {
                row[j - 1] = resultSet.getObject(j);
            }
            rows.add(row);
        }
        Object[][] values = new Object[rows.size()][];
        int i = 0;
        for (Object[] row: rows) {
            values[i] = row;
            i++;
        }
        return values;
    }
}
