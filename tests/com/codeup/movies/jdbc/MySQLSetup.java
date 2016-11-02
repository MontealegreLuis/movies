/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.dbunit.DatabaseUnitException;
import org.dbunit.DefaultDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class MySQLSetup {
    public static void loadDataSet(String path) throws Exception {
        cleanlyInsert(new FlatXmlDataSetBuilder().build(new FileInputStream(path)));
    }

    private static void cleanlyInsert(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new DefaultDatabaseTester(connection());
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    private static DatabaseConnection connection()
        throws IOException, SQLException, DatabaseUnitException {
        MysqlDataSource dataSource = dataSource();

        DatabaseConnection dbConnection = new DatabaseConnection(
            dataSource.getConnection(), dataSource.getDatabaseName()
        );

        DatabaseConfig dbConfig = dbConnection.getConfig();
        dbConfig.setProperty(
            DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory()
        );
        dbConfig.setProperty(
            DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler()
        );

        return dbConnection;
    }

    public static void truncate(
        String... tables
    ) throws SQLException, IOException {
        Statement statement = dataSource().getConnection().createStatement();
        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        for (String table : tables) {
            statement.executeUpdate(String.format("TRUNCATE %s", table));
        }
        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
    }

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