/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.builders.queries;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TableTest {
    private Table table;

    @Test
    public void it_converts_to_sql_a_table_without_alias() {
        table = Table.named("users");

        assertEquals("users", table.toSQL());
    }

    @Test(expected = IllegalArgumentException.class)
    public void it_does_not_create_a_table_with_an_empty_name() {
        table = Table.named("    ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void it_does_not_create_a_table_with_alias_as_part_of_the_table_name() {
        table = Table.named("users u");
    }

    @Test
    public void it_converts_to_sql_a_table_with_an_alias() {
        table = Table.withAlias("users", "u");

        assertEquals("users u", table.toSQL());
    }

    @Test
    public void it_adds_an_alias_to_an_existing_table() {
        table = Table.named("users");

        table.addAlias("u");

        assertEquals("users u", table.toSQL());
    }
}
