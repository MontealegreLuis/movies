/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;


public class JoinExpression implements HasSQLRepresentation {
    private final String table;
    private final String on;
    private final Type type;

    public String toSQL() {
        return String.format(
            "%s JOIN %s ON %s",
            type.toString(),
            table,
            on
        );
    }

    enum Type {INNER, OUTER};

    public JoinExpression(String table, String on, Type type) {
        this.table = table;
        this.on = on;
        this.type = type;
    }
}
