/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.builders.queries;

import com.codeup.db.builders.HasSQLRepresentation;

public class Select implements HasSQLRepresentation {
    private Columns columns;
    private String table;
    private Where where;
    private Join join;

    private Select(String table) {
        columns = Columns.empty().defaultTo("*");
        where = Where.empty();
        join = Join.empty();
        this.table = table;
    }

    public static Select from(String table) {
        return new Select(table);
    }

    public Select columns(String ...columns) {
        this.columns.add(columns);
        return this;
    }

    public Select where(String expression) {
        where.and(expression);
        return this;
    }

    public Select where(String column, int parametersCount) {
        where.and(column, parametersCount);
        return this;
    }

    public Select orWhere(String expression) {
        where.or(expression);
        return this;
    }

    public Select orWhere(String column, int parametersCount) {
        where.or(column, parametersCount);
        return this;
    }

    public Select join(String table, String on) {
        join.inner(table, on);
        return this;
    }

    public Select outerJoin(String table, String on) {
        join.outer(table, on);
        return this;
    }

    @Override
    public String toSQL() {
        return String.format(
            "SELECT %s FROM %s %s %s",
            columns.toSQL(),
            table,
            join.toSQL(),
            where.toSQL()
        ).trim().replaceAll("( )+", " ");
    }
}
