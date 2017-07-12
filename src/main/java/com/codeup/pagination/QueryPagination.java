/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.pagination;

import com.dbal.jdbc.statements.SelectStatement;

import java.util.List;

public class QueryPagination<T> implements PaginationStorage<T> {
    private SelectStatement<T> query;
    private final Object[] parameters;

    public QueryPagination(SelectStatement<T> query, Object... parameters) {
        this.query = query;
        this.parameters = parameters;
    }

    @Override
    public long itemsCount() {
        // Is it call dependent?
        // Is it keeping the count when calling the elements last?
        return new SelectStatement<>(query)
            .count()
            .execute(parameters)
            .fetchLong()
        ;
    }

    @Override
    public List<T> slice(int offset, int length) {
        return new SelectStatement<>(query)
            .offset(offset)
            .limit(length)
            .execute(parameters)
            .fetchAll()
        ;
    }
}
