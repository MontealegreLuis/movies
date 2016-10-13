/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies;

import com.codeup.db.builders.queries.Select;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MoviesCriteriaTest {
    @Test
    public void it_does_not_modify_select_if_no_category_is_provided() {
        MoviesCriteria criteria = MoviesCriteria.from(Collections.emptyMap());
        Select select = Select.from("movies m").columns("m.*");

        criteria.applyTo(select);

        assertEquals("SELECT m.* FROM movies m", select.toSQL());
    }

    @Test
    public void it_modify_select_if_category_is_provided() {
        Map<String, String[]> map = new LinkedHashMap<>();
        map.put("category", new String[]{"6"});
        MoviesCriteria criteria = MoviesCriteria.from(map);
        Select select = Select.from("movies m").columns("m.*");

        criteria.applyTo(select);

        assertEquals(
            "SELECT m.* FROM movies m INNER JOIN movies_categories mc ON mc.movie_id = m.id WHERE mc.category_id = ?",
            select.toSQL()
        );
    }
}
