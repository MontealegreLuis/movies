/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.setup;

import com.codeup.movies.*;
import com.codeup.movies.jdbc.JdbcCategories;
import com.codeup.movies.jdbc.JdbcMovies;

import java.sql.Connection;
import java.sql.SQLException;

class MoviesSeeder {
    private final Connection connection;

    MoviesSeeder(Connection connection) {
        this.connection = connection;
    }

    void seed() throws SQLException {
        JdbcMovies movies = new JdbcMovies(connection);
        JdbcCategories categories = new JdbcCategories(connection);

        for (String[] movieInformation : catalog()) {
            movies.add(Movie.titled(
                movieInformation[0],
                categories.named(movieInformation[1])
            ));
        }
    }

    private String[][] catalog() {
        return new String[][]{
                {"Citizen Kane", "drama"},
                {"Casablanca", "drama"},
                {"The Godfather", "drama"},
                {"Gone With The Wind", "drama"},
                {"Lawrence Of Arabia", "drama"},
                {"The Wizard Of Oz", "musical"},
                {"The Graduate", "drama"},
                {"On The Waterfront", "drama"},
                {"Schindler's List", "drama"},
                {"Singin' In The Rain", "musical"},
                {"It's A Wonderful Life", "drama"},
                {"Sunset Boulevard", "drama"},
                {"The Bridge On The River Kwai", "drama"},
                {"Some Like It Hot", "drama"},
                {"Star Wars", "scifi"},
                {"All About Eve", "drama"},
                {"The African Queen", "drama"},
                {"Psycho", "horror"},
                {"Chinatown", "drama"},
                {"One Flew Over The Cuckoo's Nest", "drama"},
                {"The Grapes Of Wrath", "drama"},
                {"2001: A Space Odyssey", "scifi"},
                {"The Maltese Falcon", "drama"},
                {"Raging Bull", "drama"},
                {"E.T. The extra-terrestrial", "scifi"},
                {"Dr. Strangelove", "drama"},
                {"Bonnie And Clyde", "drama"},
                {"Apocalypse Now", "drama"},
                {"Mr. Smith Goes to Washington", "drama"},
                {"The Treasure Of The Sierra Madre", "drama"},
                {"Annie Hall", "comedy"},
                {"The Godfather Part II", "drama"},
                {"High Noon", "drama"},
                {"To Kill A Mockingbird", "drama"},
                {"It Happened One Night", "drama"},
                {"Midnight Cowboy", "drama"},
                {"The Best Years Of Our Lives", "drama"},
                {"Double Indemnity", "drama"},
                {"Doctor Zhivago", "drama"},
                {"North By Northwest", "drama"},
                {"West Side Story", "musical"},
                {"Rear Window", "drama"},
                {"King Kong", "horror"},
                {"The Birth Of A Nation", "drama"},
                {"A Streetcar Named Desire", "drama"},
                {"A Clockwork Orange", "scifi"},
                {"Taxi Driver", "drama"},
                {"Jaws", "horror"},
                {"Snow White And The Seven Dwarfs", "animated"},
                {"Butch Cassidy And The Sundance Kid", "drama"},
                {"The Philadelphia Story", "drama"},
                {"From Here To Eternity", "drama"},
                {"Amadeus", "drama"},
                {"All Quiet On The Western Front", "drama"},
                {"The Sound Of Music", "musical"},
                {"M*A*S*H", "comedy"},
                {"The Third Man", "drama"},
                {"Fantasia", "animated"},
                {"Rebel Without A Cause", "drama"},
                {"Raiders Of The Lost Ark", "drama"},
                {"Vertigo", "drama"},
                {"Tootsie", "comedy"},
                {"Stagecoach", "drama"},
                {"Close Encounters Of The Third Kind", "scifi"},
                {"The Silence Of The Lambs", "horror"},
                {"Network", "drama"},
                {"The Manchurian Candidate", "drama"},
                {"An American In Paris", "drama"},
                {"Shane", "drama"},
                {"The French Connection", "drama"},
                {"Forrest Gump", "drama"},
                {"Ben-Hur", "drama"},
                {"Wuthering Heights", "drama"},
                {"The Gold Rush", "drama"},
                {"Dances With Wolves", "drama"},
                {"City Lights", "drama"},
                {"American Graffiti", "drama"},
                {"Rocky", "drama"},
                {"The Deer Hunter", "drama"},
                {"The Wild Bunch", "drama"},
                {"Modern Times", "drama"},
                {"Giant", "drama"},
                {"Platoon", "drama"},
                {"Fargo", "drama"},
                {"Duck Soup", "comedy"},
                {"Mutiny On The Bounty", "drama"},
                {"Frankenstein", "horror"},
                {"Easy Rider", "drama"},
                {"Patton", "drama"},
                {"The Jazz Singer", "drama"},
                {"My Fair Lady", "musical"},
                {"A Place In The Sun", "drama"},
                {"The Apartment", "drama"},
                {"Goodfellas", "drama"},
                {"Pulp Fiction", "drama"},
                {"The Searchers", "drama"},
                {"Bringing Up Baby", "drama"},
                {"Unforgiven", "drama"},
                {"Guess Who's Coming To Dinner", "drama"},
                {"Yankee Doodle Dandy", "musical"}
            };
    }
}
