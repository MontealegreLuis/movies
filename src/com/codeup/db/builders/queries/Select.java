/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.builders.queries;

import com.codeup.db.builders.HasSQLRepresentation;

public class Select implements HasSQLRepresentation {
    private Columns columns;
    private From from;
    private Where where;
    private Join join;
    private int limit;
    private int offset;
    private boolean determineCount = false;

    private Select(From from) {
        this.from = from;
        columns = Columns.empty().defaultTo("*");
        where = Where.empty();
        join = Join.empty();
        limit = -1;
        offset = -1;
    }

    /**
     * Create a 'cloned' copy of the given select statement
     *
     * @param select
     */
    public Select(Select select) {
        from = select.from;
        columns = new Columns(select.columns);
        where = select.where;
        join = select.join;
        limit = select.limit;
        offset = select.offset;
        determineCount = select.determineCount;
    }

    public static Select from(String table) {
        return new Select(From.table(table));
    }

    public static Select from(String table, String alias) {
        return new Select(From.tableWithAlias(table, alias));
    }

    /**
     * Add alias to original table name in order to remove ambiguity, possibly due to
     * a criteria object trying to add a join. For instance:
     *
     * `Select.from("users").addTableAlias("u").toSQL()`
     *
     * will result in:
     *
     * `SELECT * FROM users u`
     *
     * @param alias
     * @return Select
     */
    public Select addTableAlias(String alias) {
        from.addAlias(alias);
        return this;
    }

    public Select addColumns(String... columns) {
        this.columns.add(columns);
        return this;
    }

    public Select columns(String... columns) {
        this.columns.clear().add(columns);
        return this;
    }

    public Select count() {
        determineCount = true;
        return this;
    }

    private String alias() {
        return from.addAlias();
    }

    public Select where(String expression) {
        where.and(expression);
        return this;
    }

    /**
     * Generates WHERE IN clause.
     *
     * `select.where("name", 3)`
     *
     * will generate
     *
     * `AND name IN (?, ?, ?)`
     *
     * @param column Column name
     * @param parametersCount Count of `?` parameters in the `IN` clause
     * @return
     */
    public Select where(String column, int parametersCount) {
        where.and(column, parametersCount);
        return this;
    }

    public Select orWhere(String expression) {
        where.or(expression);
        return this;
    }

    /**
     * Generates WHERE IN clause.
     *
     * `select.where("name", 3)`
     *
     * will generate
     *
     * `OR name IN (?, ?, ?)`
     *
     * @param column Column name
     * @param parametersCount Count of `?` parameters in the `IN` clause
     * @return
     */
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

    public Select limit(int limit) {
        this.limit = limit;
        return this;
    }

    public Select offset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public String toSQL() {
        return String.format(
            "SELECT %s FROM %s %s %s %s %s",
            columnsToSQL(),
            from.toSQL(),
            join.toSQL(),
            where.toSQL(),
            limitToSQL(),
            offsetToSQL()
        ).trim().replaceAll("( )+", " ");
    }

    private String columnsToSQL() {
        if (determineCount) {
            determineCount();
        }
        return columns.toSQL();
    }

    private void determineCount() {
        columns.clear();
        offset = -1;
        limit = -1;
        if (join.isEmpty()) {
            columns.add("COUNT(*)");
        } else {
            columns.add(String.format("COUNT(DISTINCT %s.id)", alias()));
        }
    }

    private String offsetToSQL() {
        if (offset < 0) return "";

        return String.format("OFFSET %d", offset);
    }

    private String limitToSQL() {
        if (limit < 0) return "";

        return String.format("LIMIT %d", limit);
    }
}
