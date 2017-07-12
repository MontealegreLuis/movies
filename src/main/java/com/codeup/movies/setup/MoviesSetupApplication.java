package com.codeup.movies.setup;

import com.codeup.db.ConfigurableDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Properties;

public class MoviesSetupApplication {
    private static Properties config;

    public static void main(String[] args) {
        try (
            Connection connection = ConfigurableDataSource
                .fromCredentials(properties())
                .getConnection()
        ) {
            System.out.println("Creating database...");
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
        }
    }

    private static Properties properties() throws IOException {
        config = new Properties();
        Path path = Paths.get("src/main/resources/application.properties");
        config.load(new FileInputStream(path.toAbsolutePath().toString()));
        return config;
    }
}
