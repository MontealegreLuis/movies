/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies;

import com.codeup.db.statements.CompoundCriteria;
import com.codeup.db.statements.PaginationCriteria;
import com.codeup.movies.jdbc.MoviesInCategoryCriteria;

import java.util.Map;

public class MoviesCriteria extends CompoundCriteria {
    public MoviesCriteria(Map<String, String[]> request, int pageSize) {
        super(
            new MoviesInCategoryCriteria(request),
            PaginationCriteria.with(request, pageSize)
        );
    }
}
