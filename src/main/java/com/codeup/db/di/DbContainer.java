package com.codeup.db.di;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbContainer {
    private static Connection connection;
    private static DataSource dataSource;

    public static Connection connection() throws SQLException, NamingException {
        if (connection == null) {
            connection = dataSource().getConnection();
        }
        return connection;
    }

    private static DataSource dataSource() throws NamingException {
        if (dataSource == null) {
            Context context = (Context) new InitialContext().lookup("java:/comp/env");

            dataSource = (DataSource) context.lookup("jdbc/movies_db");
        }

        return dataSource;
    }
}
