/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.movies.Category;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class JdbcCategoriesTest extends MySQLTestCase {

    private JdbcCategories categories;

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder()
            .build(new FileInputStream("tests/resources/categories.xml"))
        ;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        categories = new JdbcCategories(dataSource().getConnection());
    }

    @Override
    protected void setupTables() throws IOException, SQLException {
        truncate("movies_categories", "categories", "movies");
    }

    public void testFindsExistingCategory() throws SQLException, IOException {
        Category thriller = categories.named("thriller");
        assertNotNull(thriller);
        assertEquals("thriller", thriller.name());
    }

    public void testDoesNotFindUnknownCategory() throws SQLException, IOException {
        Category unknown = categories.named("this is an UNKNOWN category");
        assertNull(unknown);
    }
}
