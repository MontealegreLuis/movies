/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.builders.queries;

import com.codeup.db.builders.HasSQLRepresentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.codeup.db.builders.queries.JoinExpression.*;

public class Select implements HasSQLRepresentation {
    private List<String> columns;
    private String table;
    private List<WhereExpression> whereExpressions;
    private List<JoinExpression> joins;

    private Select(String table) {
        columns = new ArrayList<>();
        whereExpressions = new ArrayList<>();
        joins = new ArrayList<>();
        this.table = table;
    }

    public static Select from(String table) {
        return new Select(table);
    }

    @Override
    public String toSQL() {
        return String.format(
            "SELECT %s FROM %s%s%s",
            columnsToString(),
            table,
            joinsToString(),
            whereToString()
        );
    }

    public Select select(String ...columns) {
        Collections.addAll(this.columns, columns);
        return this;
    }

    public Select where(String expression) {
        addWhere(expression, WhereExpression.Operator.AND);
        return this;
    }

    public Select whereIn(String column, int parametersCount) {
        addWhere(String.format(
            "%s IN %s", column, ParameterPlaceholders.generate(parametersCount)
        ), null);
        return this;
    }

    public Select orWhere(String expression) {
        addWhere(expression, WhereExpression.Operator.OR);
        return this;
    }

    private void addWhere(String expression, WhereExpression.Operator operator) {
        if (whereExpressions.size() == 0) {
            operator = null;
        }
        whereExpressions.add(WhereExpression.with(expression, operator));
    }

    public Select join(String table, String on) {
        joins.add(new JoinExpression(table, on, Type.INNER));
        return this;
    }

    private String whereToString() {
        StringBuilder where = new StringBuilder();
        whereExpressions
            .forEach(expression -> where.append(expression.toSQL()).append(" "))
        ;
        return (whereExpressions.size() > 0 ? " WHERE " : "")
            + where.toString().replaceAll(" $", "")
        ;
    }

    private String columnsToString() {
        StringBuilder columns = new StringBuilder();
        if (this.columns.isEmpty()) {
            this.columns.add("*");
        }
        this.columns.forEach(column -> columns.append(column).append(", "));
        return columns.toString().replaceAll(", $", "");
    }

    private String joinsToString() {
        StringBuilder joinClauses = new StringBuilder().append(" ");
        joins.forEach(join -> joinClauses.append(join.toSQL()).append(" "));
        return joinClauses.toString().replaceAll(" $", "");
    }
}
