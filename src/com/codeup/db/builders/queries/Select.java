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
    private List<WhereExpression> whereExpressions;
    private List<JoinExpression> joins;

    private Select(String table) {
        columns = Columns.empty();
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

    public Select columns(String ...columns) {
        this.columns.add(columns);
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
        if (columns.isEmpty()) {
            columns.add("*");
        }
        return this.columns.toSQL();
    }

    private String joinsToString() {
        StringBuilder joinClauses = new StringBuilder().append(" ");
        joins.forEach(join -> joinClauses.append(join.toSQL()).append(" "));
        return joinClauses.toString().replaceAll(" $", "");
    }
}
