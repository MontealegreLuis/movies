/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies;

import com.codeup.db.builders.queries.Select;
import com.codeup.db.statements.Criteria;

import java.util.Map;

public class MoviesCriteria implements Criteria {
    private int categoryId;

    private MoviesCriteria(int categoryId) {
        this.categoryId = categoryId;
    }

    public static MoviesCriteria from(Map<String, String[]> values) {
        int category = values.containsKey("category")
            ? Integer.parseInt(values.get("category")[0])
            : -1
        ;

        return new MoviesCriteria(category);
    }

    public void applyTo(Select select) {
        if (hasCategory()) {
            select
                .join("movies_categories mc", "mc.movie_id = m.id")
                .where("mc.category_id = ?")
            ;
        }
    }

    public Object[] arguments() {
        if (categoryId ==  -1) return new Object[0];

        return new Object[]{categoryId};
    }

    private boolean hasCategory() {
        return categoryId != -1;
    }
}
