/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies;

import com.codeup.db.builders.queries.Select;

import java.util.Map;

public class MoviesCriteria {
    private int categoryId;
    private int page;

    private MoviesCriteria(int categoryId, int page) {
        this.categoryId = categoryId;
        this.page = page;
    }

    public static MoviesCriteria from(Map<String, String[]> values) {
        int category = values.containsKey("category")
            ? Integer.parseInt(values.get("category")[0])
            : -1
        ;
        int page = 1;

        return new MoviesCriteria(category, page);
    }

    public void applyTo(Select select) {
        if (hasCategory()) {
            select
                .join("movies_categories mc", "mc.movie_id = m.id")
                .where("mc.category_id = ?")
            ;
        }
    }

    private boolean hasCategory() {
        return categoryId != -1;
    }

    public Object[] arguments() {
        if (categoryId ==  -1) return new Object[0];

        return new Object[]{categoryId};
    }
}
