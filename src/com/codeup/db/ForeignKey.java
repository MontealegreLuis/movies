/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

public class ForeignKey implements HasSQLRepresentation {
    private final Column column;
    private String foreignKeyName;
    private String table;

    ForeignKey(Column column) {
        this.column = column;
    }

    public ForeignKey references(String name) {
        this.foreignKeyName = name;
        return this;
    }

    public ForeignKey on(String table) {
        this.table = table;
        return this;
    }

    @Override
    public String toSQL() {
        return String.format(
            "FOREIGN KEY(%s) REFERENCES %s(%s)",
            column.name(),
            table,
            foreignKeyName
        );
    }
}
