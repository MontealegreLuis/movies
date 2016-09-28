/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.actions;

import com.codeup.movies.*;

import java.util.List;

public class ViewMovies {
    private final Movies movies;
    private final Categories categories;

    public ViewMovies(Movies movies, Categories categories) {
        this.movies = movies;
        this.categories = categories;
    }

    public MoviesInformation view(String category) {
        return new MoviesInformation(
            categories.all(),
            getMovies(movies, category)
        );
    }

    private List<Movie> getMovies(Movies movies, String category) {
        if (category != null && !category.isEmpty()) {
            return movies.inCategory(category);
        }
        return movies.all();
    }
}
