/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.movies.setup.MoviesMigration;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@RunWith(Suite.class)
@Suite.SuiteClasses({JdbcCategoriesTest.class})
public class DatabaseRuleSuite {
    @ClassRule
    public static ExternalResource testRule = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            MoviesMigration migration = new MoviesMigration(dataSource().getConnection());
            migration.up();
            System.out.println("Creating database tables, if needed...");
        };

        @Override
        protected void after() {
            System.out.println("Finishing database test suite...");
        };
    };

    public static MysqlDataSource dataSource() throws IOException {
        Properties config = new Properties();
        config.load(new FileInputStream("config.properties"));

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(config.getProperty("url"));
        dataSource.setUser(config.getProperty("user"));
        dataSource.setPassword(config.getProperty("password"));
        dataSource.setDatabaseName(config.getProperty("database"));

        return dataSource;
    }
}
