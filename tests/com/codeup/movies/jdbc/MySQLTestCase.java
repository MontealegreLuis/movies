/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import static com.codeup.movies.jdbc.DatabaseRuleSuite.*;

abstract public class MySQLTestCase extends DatabaseTestCase {
    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        MysqlDataSource dataSource = dataSource();

        DatabaseConnection dbConnection = new DatabaseConnection(
            dataSource.getConnection(), dataSource.getDatabaseName()
        );

        setupTables();

        DatabaseConfig dbConfig = dbConnection.getConfig();
        dbConfig.setProperty(
            DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory()
        );
        dbConfig.setProperty(
            DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler()
        );

        return dbConnection;
    }

    protected abstract void setupTables() throws IOException, SQLException;

    protected void truncate(
        String... tables
    ) throws SQLException, IOException {
        Statement statement = dataSource().getConnection().createStatement();
        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        for (String table : tables) {
            statement.executeUpdate(String.format("TRUNCATE %s", table));
        }
        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
    }
}
