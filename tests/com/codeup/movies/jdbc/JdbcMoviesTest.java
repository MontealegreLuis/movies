/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.tests.MySQLSetup;
import com.codeup.movies.Movie;
import com.codeup.movies.MoviesCriteria;
import com.codeup.pagination.Pagination;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JdbcMoviesTest {
    private JdbcMovies movies;

    @Before
    public void loadFixtures() throws Exception {
        MySQLSetup.truncate("movies_categories", "categories", "movies");
        MySQLSetup.loadDataSet("tests/resources/movies.xml");

        movies = new JdbcMovies(MySQLSetup.dataSource().getConnection());
    }

    @Test
    public void it_can_find_an_existing_movie_by_its_id() {
        int existingId = 2;

        Movie movie = movies.with(existingId);

        assertNotNull(movie);
        assertEquals("Home", movie.title());
        assertEquals(4, movie.rating());
        assertEquals(2, movie.categories().size());
    }

    @Test
    public void it_cannot_find_unknown_movie() {
        int unknownId = 10;

        assertNull(movies.with(unknownId));
    }

    @Test
    public void it_updates_a_movie() {
        int movieId = 1;
        int newRating = 2;
        Movie movie = movies.with(movieId);

        movie.rate(newRating);
        movies.update(movie);

        assertEquals(newRating, movies.with(movieId).rating());
    }

    @Test
    public void it_finds_all_movies_of_a_given_category() {
        Map<String, String[]> request = new HashMap<>();
        request.put("category", new String[]{"1"});
        MoviesCriteria criteria = new MoviesCriteria(request);
        int page =1;

        Pagination<Movie> moviesPagination = movies.matching(criteria, page);
        List<Movie> matchingMovies = moviesPagination.pageItems();

        assertEquals("Wrong number of pages", 1, moviesPagination.pagesCount());
        assertEquals("Wrong number of matching movies", 1, matchingMovies.size());
        assertEquals("Seven", matchingMovies.get(0).title());
    }

    @Test
    public void it_adds_a_new_movie() {
        Movie movie = new Movie("Shrek", 5, "shrek.png", Collections.emptyList());

        Movie shrek = movies.add(movie);
        Movie savedMovie = movies.with((int) shrek.id());

        assertEquals(shrek.id(), savedMovie.id());
        assertEquals(shrek.rating(), savedMovie.rating());
        assertEquals(shrek.thumbnail(), savedMovie.thumbnail());
    }
}
