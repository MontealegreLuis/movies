/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies;

import com.codeup.db.builders.queries.Select;
import com.codeup.movies.jdbc.MoviesInCategoryCriteria;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MoviesInCategoryCriteriaTest {
    private MoviesInCategoryCriteria criteria;

    @Test
    public void it_does_not_apply_if_no_category_is_provided() {
        criteria = new MoviesInCategoryCriteria(Collections.emptyMap());

        criteria.applyTo(Select.from("movies"));

        assertEquals(0, criteria.arguments().size());
    }

    @Test
    public void it_applies_if_category_is_provided() {
        Map<String, String[]> request = new LinkedHashMap<>();
        request.put("category", new String[]{"6"});
        criteria = new MoviesInCategoryCriteria(request);
        Select select = Select.from("movies", "m").columns("m.*");

        criteria.applyTo(select);

        assertEquals(1, criteria.arguments().size());
        assertEquals(6, (int) criteria.arguments().get(0));
        assertEquals(
            "SELECT m.* FROM movies m INNER JOIN movies_categories mc ON mc.movie_id = m.id WHERE mc.category_id = ?",
            select.toSQL()
        );
    }
}
