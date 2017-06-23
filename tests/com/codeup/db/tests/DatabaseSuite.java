/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.tests;

import com.codeup.auth.infrastructure.jdbc.JdbcUsersTest;
import com.codeup.db.ConfigurableDataSource;
import com.codeup.movies.jdbc.JdbcCategoriesTest;
import com.codeup.movies.jdbc.JdbcMoviesTest;
import com.codeup.movies.setup.MoviesMigration;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.sql.Connection;
import java.util.Properties;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    JdbcCategoriesTest.class,
    JdbcMoviesTest.class,
    JdbcUsersTest.class
})
public class DatabaseSuite {
    @ClassRule
    public static ExternalResource testRule = new ExternalResource() {
        @Override
        protected void before() throws Throwable {

            Properties configuration = MySQLSetup.configuration();
            try (
                Connection connection = ConfigurableDataSource
                    .fromCredentials(configuration)
                    .getConnection()
            ) {
                MySQLSetup.createDatabaseIfNotExists(
                    connection,
                    configuration.getProperty("database")
                );
                new MoviesMigration(connection).up();
            }
            System.out.println("Creating database tables, if needed...");
        }

        @Override
        protected void after() {
            System.out.println("Finishing database test suite...");
        }
    };
}
