/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.actions;

import com.codeup.movies.*;

import java.util.List;

public class AddMovie {
    private final Categories categories;
    private final Movies movies;

    public AddMovie(Categories categories, Movies movies) {
        this.categories = categories;
        this.movies = movies;
    }

    public void add(String title, int rating, String[] categoriesIds) {
        movies.add(new Movie(
            title,
            rating,
            categories.in(categoriesIds)
        ));
    }

    public List<Category> categories() {
        return categories.all();
    }
}
