package com.codeup.db.di;

import com.codeup.db.MySQLConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DbContainer {
    private static Connection connection;

    public static Connection connection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            connection = new MySQLConnection("root", "Codeup1!", "movies_db")
                .connect()
            ;
        }
        return connection;
    }
}
