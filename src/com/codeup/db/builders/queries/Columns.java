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

    private Columns() {
        columns = new ArrayList<>();
    }

    public static Columns empty() {
        return new Columns();
    }

    public void add(String... columns) {
        Collections.addAll(this.columns, columns);
    }

    public int size() {
        return columns.size();
    }

    public boolean isEmpty() {
        return columns.isEmpty();
    }

    @Override
    public String toSQL() {
        return String.join(", ", columns.toArray(new String[]{}));
    }
}
