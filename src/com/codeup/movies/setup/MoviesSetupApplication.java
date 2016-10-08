package com.codeup.movies.setup;

import com.codeup.db.MySQLConnection;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

public class MoviesSetupApplication {
    public static void main(String[] args) {
        Properties config = new Properties();
        MySQLConnection databaseConnection = null;

        try {

            config.load(new FileInputStream("config.properties"));

            databaseConnection = new MySQLConnection(
                config.getProperty("user"),
                config.getProperty("password")
            );

            Connection connection = databaseConnection.connect();
            System.out.println("Creating movies database...");
            new MoviesDatabase(connection).create(config.getProperty("database"));
            System.out.println("Creating tables...");
            new MoviesMigration(connection).up();
            System.out.println("Seeding database...");
            new CategoriesSeeder(connection).seed();
            new MoviesSeeder(connection).seed();
            new UsersSeeder(connection).seed();
            System.out.println("Done!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.close();
        }
    }
}
