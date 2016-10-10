/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.RowMapper;
import com.codeup.movies.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MoviesMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet) throws SQLException {
        return new Movie(
            resultSet.getInt("id"),
            resultSet.getString("title"),
            resultSet.getInt("rating"),
            resultSet.getString("thumbnail")
        );
    }

    @Override
    public Map<Integer, Object> mapColumns(Movie movie) {
        Map<Integer, Object> mapping = new LinkedHashMap<>();
        mapping.put(1, movie.title());
        mapping.put(2, movie.rating());
        mapping.put(3, movie.thumbnail());
        return mapping;
    }

    @Override
    public Map<Integer, Object> mapIdentifier(Movie movie) {
        Map<Integer, Object> mapping = new LinkedHashMap<>();
        mapping.put(3, movie.id());
        return mapping;
    }
}
