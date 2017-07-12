/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.tests;

import com.dbal.jdbc.Database;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MySQLSetup {
    private static Properties config;

    public static void truncate(
        MysqlDataSource source,
        String... tables
    ) throws SQLException, IOException {
        try (
            Connection connection = source.getConnection();
            Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            for (String table : tables) {
                statement.executeUpdate(String.format("TRUNCATE %s", table));
            }
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    public static void loadDataSet(
        MysqlDataSource source,
        String path
    ) throws Exception {
        cleanlyInsert(
            source,
            new FlatXmlDataSetBuilder().build(new FileInputStream(path))
        );
    }

    private static void cleanlyInsert(
        MysqlDataSource source,
        IDataSet dataSet
    ) throws Exception {
        IDatabaseTester databaseTester = new DefaultDatabaseTester(connection(source));
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    private static DatabaseConnection connection(MysqlDataSource source)
        throws IOException, SQLException, DatabaseUnitException {

        DatabaseConnection dbConnection = new DatabaseConnection(
            source.getConnection(), source.getDatabaseName()
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

    static void createDatabaseIfNotExists(
        Connection connection,
        String databaseName
    ) throws SQLException {
        Database database = new Database(connection);
        database.create(databaseName);
        database.use(databaseName);
    }

    public static Properties configuration() throws IOException {
        if (config != null) return config;

        config = new Properties();
        Path path = Paths.get("src/test/resources/tests.properties");
        config.load(new FileInputStream(path.toAbsolutePath().toString()));

        return config;
    }
}
