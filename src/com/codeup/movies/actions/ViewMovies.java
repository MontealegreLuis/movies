/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.actions;

import com.codeup.movies.*;

public class ViewMovies {
    private final Movies movies;
    private final Categories categories;

    public ViewMovies(Movies movies, Categories categories) {
        this.movies = movies;
        this.categories = categories;
    }

    public MoviesInformation view(MoviesCriteria criteria) {
        return new MoviesInformation(
            categories.all(),
            movies.matching(criteria)
        );
    }
}
