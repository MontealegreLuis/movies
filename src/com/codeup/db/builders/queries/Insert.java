/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.builders.queries;

import com.codeup.db.builders.HasSQLRepresentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Insert implements HasSQLRepresentation {
    private String table;
    private int valuesCount;
    private List<String> columns;

    private Insert(String table) {
        this.table = table;
        columns = new ArrayList<>();
    }

    public static Insert into(String table) {
        return new Insert(table);
    }

    public Insert values(int valuesCount) {
        this.valuesCount = valuesCount;
        return this;
    }

    public Insert columns(String... columns) {
        Collections.addAll(this.columns, columns);
        return this;
    }

    public String toSQL() {
        assertValuesArePresent();
        assertColumnsAndValuesMatch();
        return String.format(
            "INSERT INTO %s %s %s",
            table,
            columnsToString(),
            valuesToString()
        ).replaceAll("( )+", " ");
    }

    private void assertColumnsAndValuesMatch() {
        if (columns.size() > 0 && valuesCount() != columns.size()) {
            throw new IllegalStateException("Columns and values count do not match");
        }
    }

    private void assertValuesArePresent() {
        if (valuesCount() == 0) {
            throw new IllegalStateException("Cannot build INSERT without values");
        }
    }

    private String columnsToString() {
        if (columns.size() == 0) return "";

        return "(" + String.join(", ", columns.toArray(new String[]{})) + ")";
    }

    private String valuesToString() {
        return "VALUES " + ParameterPlaceholders.generate(valuesCount());
    }

    private int valuesCount() {
        if (valuesCount == 0) {
            valuesCount = columns.size();
        }
        return valuesCount;
    }
}
