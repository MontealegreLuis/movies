/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.actions;

import com.codeup.movies.Movie;
import com.codeup.movies.Movies;

public class ViewMovie {
    private final Movies movies;

    public ViewMovie(Movies movies) {
        this.movies = movies;
    }

    public Movie view(int id) {
        return movies.with(id);
    }
}
