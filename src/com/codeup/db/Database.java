/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db;

import java.io.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final Connection connection;

    public Database(Connection connection) {
        this.connection = connection;
    }

    public void create(String database) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
            String.format("CREATE DATABASE IF NOT EXISTS %s", database)
        );
        statement.close();
    }

    public void drop(String database) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
            String.format("DROP DATABASE IF EXISTS %s", database)
        );
        statement.close();
    }

    public void use(String database) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(String.format("USE %s", database));
        statement.close();
    }

    public void importFile(
        String sqlFile
    ) throws IOException, SQLException {
        String line;
        StringBuilder queries = new StringBuilder();

        BufferedReader reader = new BufferedReader(new FileReader(
            new File(sqlFile)
        ));
        while ((line = reader.readLine()) != null) {
            queries.append(line).append(" ");
        }
        reader.close();

        Statement statement = connection.createStatement();

        String[] ddlStatements = queries.toString().split(";");
        for (String query : ddlStatements) {
            if (!query.trim().isEmpty()) {
                statement.executeUpdate(query);
            }
        }
        statement.close();
    }
}
