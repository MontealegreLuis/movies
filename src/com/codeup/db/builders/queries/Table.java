/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.builders.queries;

import com.codeup.db.builders.HasSQLRepresentation;

public class Table implements HasSQLRepresentation {
    private String table;
    private String alias;

    private Table(String table, String alias) {
        assertValidTableName(table);
        this.table = table;
        this.alias = alias;
    }

    private void assertValidTableName(String table) {
        if (table.trim().length() > 0 && table.indexOf(' ') == -1) return;

        throw new IllegalArgumentException("Invalid table name given");
    }

    static Table named(String table) {
        return new Table(table, null);
    }

    static Table withAlias(String table, String alias) {
        return new Table(table, alias);
    }

    void addAlias(String alias) {
        this.alias = alias;
    }

    String addAlias() {
        if (alias == null) return Character.toString(table.charAt(0)).toLowerCase();

        return alias;
    }

    public String toSQL() {
        return table + ((alias == null) ? "" : " " + alias);
    }
}
