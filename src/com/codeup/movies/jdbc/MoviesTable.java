/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.RowMapper;
import com.codeup.db.Table;
import com.codeup.movies.Movie;

import java.sql.Connection;

public class MoviesTable extends Table<Movie> {
    public MoviesTable(Connection connection) {
        super(connection);
    }

    @Override
    protected String table() {
        return "movies";
    }

    @Override
    protected RowMapper<Movie> mapper() {
        return new MoviesMapper();
    }
}
