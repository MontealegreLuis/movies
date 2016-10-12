/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.builders.queries;

import com.codeup.db.builders.HasSQLRepresentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Columns implements HasSQLRepresentation {
    private List<String> columns;
    private String defaultColumn;

    private Columns() {
        columns = new ArrayList<>();
    }

    public static Columns empty() {
        return new Columns();
    }

    public Columns defaultTo(String column) {
        defaultColumn = column;
        return this;
    }

    public Columns add(String... columns) {
        Collections.addAll(this.columns, columns);
        return this;
    }

    public int size() {
        return columns.size();
    }

    public boolean isEmpty() {
        return columns.isEmpty();
    }

    @Override
    public String toSQL() {
        if (columns.isEmpty()) columns.add(defaultColumn);

        return String.join(", ", columns.toArray(new String[]{}));
    }

    public Columns clear() {
        columns.clear();
        return this;
    }
}
