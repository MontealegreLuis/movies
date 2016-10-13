/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies;

import java.util.List;

public interface Movies {
    Movie with(int id);
    void update(Movie movie);
    Movie add(Movie movie);
    List<Movie> matching(MoviesCriteria criteria);
}
