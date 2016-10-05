/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.builders.queries;

import com.codeup.db.builders.HasSQLRepresentation;

import java.util.ArrayList;
import java.util.List;

import static com.codeup.db.builders.queries.JoinExpression.*;

public class Select implements HasSQLRepresentation {
    private Columns columns;
    private String table;
    private Where where;
    private List<JoinExpression> joins;

    private Select(String table) {
        columns = Columns.empty().defaultTo("*");
        where = Where.empty();
        joins = new ArrayList<>();
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
        joins.add(new JoinExpression(table, on, Type.INNER));
        return this;
    }

    @Override
    public String toSQL() {
        return String.format(
            "SELECT %s FROM %s %s %s",
            columns.toSQL(),
            table,
            joinsToString(),
            where.toSQL()
        ).trim().replaceAll("( )+", " ");
    }

    private String joinsToString() {
        StringBuilder joinClauses = new StringBuilder().append(" ");
        joins.forEach(join -> joinClauses.append(join.toSQL()).append(" "));
        return joinClauses.toString().replaceAll(" $", "");
    }
}
