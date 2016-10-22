/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.RowMapper;
import com.codeup.movies.Movie;

public class MoviesMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(Object[] values) {
        return new Movie(
            (long) values[0],
            values[1].toString(),
            (int) values[2],
            thumbnail(values[3])
        );
    }

    private String thumbnail(Object thumbnail) {
        return thumbnail == null ? null : thumbnail.toString();
    }
}
