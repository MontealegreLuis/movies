/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QueryBuilder implements HasSQLRepresentation {
    private List<String> select;
    private String table;
    private List<WhereExpression> whereExpressions;

    public QueryBuilder() {
        select = new ArrayList<>();
        whereExpressions = new ArrayList<>();
    }

    @Override
    public String toSQL() {
        assertFromHasBeenCalled();

        return String.format(
            "SELECT %s FROM %s%s",
            selectToString(),
            table,
            whereToString()
        );
    }

    public QueryBuilder from(String table) {
        this.table = table;
        return this;
    }

    public QueryBuilder select(String ...columns) {
        Collections.addAll(this.select, columns);
        return this;
    }

    public QueryBuilder where(String expression) {
        addWhere(expression, WhereExpression.Operator.AND);
        return this;
    }

    public QueryBuilder whereIn(String column, int parametersCount) {
        String[] parameters = new String[parametersCount];
        Arrays.fill(parameters, "?");
        addWhere(String.format(
            "%s IN (%s)", column, String.join(", ", parameters)
        ), null);
        return this;
    }

    public QueryBuilder orWhere(String expression) {
        addWhere(expression, WhereExpression.Operator.OR);
        return this;
    }

    private void addWhere(String expression, WhereExpression.Operator operator) {
        if (whereExpressions.size() == 0) {
            operator = null;
        }
        whereExpressions.add(WhereExpression.with(expression, operator));
    }

    private void assertFromHasBeenCalled() {
        if (table == null) {
            throw new IllegalStateException("No table to select from was specified");
        }
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

    private String selectToString() {
        StringBuilder columns = new StringBuilder();
        if (select.isEmpty()) {
            select.add("*");
        }
        select.forEach(column -> columns.append(column).append(", "));
        return columns.toString().replaceAll(", $", "");
    }
}
