/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.movies.Category;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;

public class JdbcCategoriesTest {

    private JdbcCategories categories;

    @Before
    public void loadFixtures() throws Exception {
        MySQLSetup.truncate("movies_categories", "categories", "movies");
        MySQLSetup.loadDataSet("tests/resources/categories.xml");
        categories = new JdbcCategories(MySQLSetup.dataSource().getConnection());
    }

    @Test
    public void testFindsExistingCategory() throws SQLException, IOException {
        Category thriller = categories.named("thriller");
        assertNotNull(thriller);
        assertEquals("thriller", thriller.name());
    }

    @Test
    public void testDoesNotFindUnknownCategory() throws SQLException, IOException {
        Category unknown = categories.named("this is an UNKNOWN category");
        assertNull(unknown);
    }
}
