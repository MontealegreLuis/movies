/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.builders.queries.Select;
import com.codeup.db.statements.Criteria;

import java.util.Map;

public class MoviesInCategoryCriteria extends Criteria {
    public MoviesInCategoryCriteria(Map<String, String[]> request) {
        super(request);
    }

    @Override
    public void applyTo(Select select) {
        if (request().containsKey("category")) {
            arguments().add(Integer.parseInt(request().get("category")[0]));
            select
                .join("movies_categories mc", "mc.movie_id = m.id")
                .where("mc.category_id = ?")
            ;
        }
    }
}
