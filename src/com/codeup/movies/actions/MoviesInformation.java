/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.actions;

import com.codeup.movies.Category;
import com.codeup.movies.Movie;

import java.util.List;

public class MoviesInformation {
    public final List<Category> categories;
    public final List<Movie> movies;

    MoviesInformation(List<Category> categories, List<Movie> movies) {
        this.categories = categories;
        this.movies = movies;
    }
}
