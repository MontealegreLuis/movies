/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies;

import com.codeup.pagination.Pagination;

public interface Movies {
    Movie with(int id);
    void update(Movie movie);
    Movie add(Movie movie);
    Pagination<Movie> matching(MoviesCriteria criteria, int page);
}
