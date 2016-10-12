/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.builders.queries;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SelectTest {

    private Select select;

    @Test
    public void it_sets_default_value_for_select_clause() {
        select = Select.from("users");
        assertEquals("SELECT * FROM users", select.toSQL());
    }

    @Test
    public void it_selects_specific_columns() {
        select = Select.from("users").columns("username", "password");
        assertEquals("SELECT username, password FROM users", select.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_single_where_expression() {
        select = Select.from("users").where("username = ?");
        assertEquals("SELECT * FROM users WHERE username = ?", select.toSQL());
    }

    @Test
    public void it_converts_to_sql_several_and_where_expressions() {
        select = Select
            .from("users")
            .where("username = ?")
            .where("password = ?")
            .where("name LIKE ?")
        ;
        assertEquals(
            "SELECT * FROM users WHERE username = ? AND password = ? AND name LIKE ?",
            select.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_several_or_where_expressions() {
        select = Select
            .from("users")
            .where("username = ?")
            .orWhere("password = ?")
            .orWhere("name LIKE ?")
        ;
        assertEquals(
            "SELECT * FROM users WHERE username = ? OR password = ? OR name LIKE ?",
            select.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_a_combination_of_where_expressions() {
        select = Select
            .from("users")
            .where("username = ?")
            .orWhere("password = ?")
            .where("name LIKE ?")
        ;
        assertEquals(
            "SELECT * FROM users WHERE username = ? OR password = ? AND name LIKE ?",
            select.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_an_in_statement() {
        select = Select.from("users").where("username", 2);
        assertEquals("SELECT * FROM users WHERE username IN (?, ?)", select.toSQL());
    }

    @Test
    public void it_converts_to_sql_an_several_in_statement() {
        select = Select
            .from("users")
            .where("username", 2)
            .orWhere("id", 3)
            .where("name", 3)
        ;
        assertEquals(
            "SELECT * FROM users WHERE username IN (?, ?) OR id IN (?, ?, ?) AND name IN (?, ?, ?)",
            select.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_a_statement_with_a_limit() {
        select = Select.from("users").limit(5);
        assertEquals("SELECT * FROM users LIMIT 5", select.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_paginated_statement() {
        select = Select.from("users").limit(5).offset(5);
        assertEquals("SELECT * FROM users LIMIT 5 OFFSET 5", select.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_join_statement() {
        select = Select.from("users u").join("roles r", "u.role_id = r.id");
        assertEquals(
            "SELECT * FROM users u INNER JOIN roles r ON u.role_id = r.id",
            select.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_several_join_statements() {
        select = Select
            .from("posts p")
            .join("posts_tags pt", "pt.post_id = p.id")
            .outerJoin("tags t", "pt.tag_id = t.id")
        ;
        assertEquals(
            "SELECT * FROM posts p INNER JOIN posts_tags pt ON pt.post_id = p.id OUTER JOIN tags t ON pt.tag_id = t.id",
            select.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_several_join_statements_with_a_where_clause() {
        select = Select
            .from("posts p")
            .outerJoin("posts_tags pt", "pt.post_id = p.id")
            .join("tags t", "pt.tag_id = t.id")
            .where("p.id = ?")
        ;
        assertEquals(
            "SELECT * FROM posts p OUTER JOIN posts_tags pt ON pt.post_id = p.id INNER JOIN tags t ON pt.tag_id = t.id WHERE p.id = ?",
            select.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_several_join_statements_with_two_where_clauses() {
        select = Select
            .from("posts p")
            .join("posts_tags pt", "pt.post_id = p.id")
            .outerJoin("tags t", "pt.tag_id = t.id")
            .where("p.id = ?")
            .where("p.created_at > ?")
        ;
        assertEquals(
            "SELECT * FROM posts p INNER JOIN posts_tags pt ON pt.post_id = p.id OUTER JOIN tags t ON pt.tag_id = t.id WHERE p.id = ? AND p.created_at > ?",
            select.toSQL()
        );
    }
}
