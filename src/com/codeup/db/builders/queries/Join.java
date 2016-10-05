/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.builders.queries;

import com.codeup.db.builders.HasSQLRepresentation;
import com.codeup.db.builders.queries.JoinExpression.Type;

import java.util.ArrayList;
import java.util.List;

public class Join implements HasSQLRepresentation {
    private List<JoinExpression> joins;

    private Join() {
        joins = new ArrayList<>();
    }

    public static Join empty() {
        return new Join();
    }

    public Join inner(String table, String on) {
        joins.add(new JoinExpression(table, on, Type.INNER));
        return this;
    }

    public Join outer(String table, String on) {
        joins.add(new JoinExpression(table, on, Type.OUTER));
        return this;
    }

    public String toSQL() {
        StringBuilder joinClauses = new StringBuilder();
        joins.forEach(join -> joinClauses.append(join.toSQL()).append(" "));
        return joinClauses.toString().replaceAll(" $", "");
    }
}