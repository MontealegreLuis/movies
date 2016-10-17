/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.statements;

import com.codeup.db.builders.queries.Select;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PaginationCriteriaTest {
    private PaginationCriteria criteria;

    @Test
    public void it_defaults_to_1_if_no_page_is_provided() {
        criteria = PaginationCriteria.with(Collections.emptyMap(), 5);

        Select select = Select.from("movies");
        criteria.applyTo(select);

        assertEquals(
            "SELECT * FROM movies LIMIT 5 OFFSET 0",
            select.toSQL()
        );
    }

    @Test
    public void it_adds_limit_and_offset_to_query() {
        Select select = Select.from("movies m").columns("m.*");
        Map<String, String[]> request = new LinkedHashMap<>();
        request.put("page", new String[]{"6"});

        criteria = PaginationCriteria.with(request, 5);
        criteria.applyTo(select);

        assertEquals(
            "SELECT m.* FROM movies m LIMIT 5 OFFSET 25",
            select.toSQL()
        );
    }
}
