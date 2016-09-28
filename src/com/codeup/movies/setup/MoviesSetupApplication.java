package com.codeup.movies.setup;

import com.codeup.db.MySQLConnection;

import java.sql.Connection;

public class MoviesSetupApplication {
    public static void main(String[] args) {
        MySQLConnection databaseConnection = new MySQLConnection(
            "root", "Codeup1!"
        );

        try {
            Connection connection = databaseConnection.connect();
            System.out.println("Creating movies database...");
            new MoviesDatabase(connection).create("movies_db");
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
