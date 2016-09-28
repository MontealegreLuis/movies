/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.actions;

import com.codeup.movies.Movie;
import com.codeup.movies.Movies;

public class RateMovie {
    private final Movies movies;

    public RateMovie(Movies movies) {
        this.movies = movies;
    }

    public void rate(int id, int rate) {
        Movie movie = movies.with(id);
        movie.rate(rate);
        movies.update(movie);
    }
}
