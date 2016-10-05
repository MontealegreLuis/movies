/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies;

import java.util.List;

public interface Movies {
    Movie with(int id);
    List<Movie> all();
    List<Movie> inCategory(String category);
    void update(Movie movie);
    Movie add(Movie movie);
}
