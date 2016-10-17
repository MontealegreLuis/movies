/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.statements;

import com.codeup.db.builders.queries.Select;

import java.util.Map;

public class PaginationCriteria extends Criteria {
    private int pageSize;
    private int currentPage;

    private PaginationCriteria(Map<String, String[]> request, int pageSize) {
        super(request);
        this.pageSize = pageSize;
    }

    public static PaginationCriteria with(
        Map<String, String[]> request,
        int pageSize
    ) {
        return new PaginationCriteria(request, pageSize);
    }

    @Override
    public void applyTo(Select select) {
        setCurrentPage();
        select.offset(offset()).limit(pageSize);
    }

    private void setCurrentPage() {
        if (request().containsKey("page")) {
            currentPage = Integer.parseInt(request().get("page")[0]);
        } else {
            currentPage  = 1;
        }
    }

    private int offset() {
        return (currentPage - 1) * pageSize;
    }
}
