/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.movies.Movie;
import com.dbal.jdbc.RowMapper;

import java.util.List;

class MoviesMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(List<Object> values) {
        return new Movie(
            (long) values.get(0),
            values.get(1).toString(),
            (int) values.get(2),
            thumbnail(values.get(3))
        );
    }

    private String thumbnail(Object thumbnail) {
        return thumbnail == null ? null : thumbnail.toString();
    }
}
