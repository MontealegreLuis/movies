/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.RowMapper;
import com.codeup.db.Table;
import com.codeup.movies.Category;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class CategoriesTable extends Table<Category> {
    CategoriesTable(Connection connection) {
        super(connection);
    }

    Category insert(String name) {
        try {
            return this.createInsert("name").execute(name).fetch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Category findBy(String name) {
        try {
            return this.select("*").where("name = ?").execute(name).fetch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Category> findAllIn(String... categories) {
        try {
            return this
                .select("*")
                .whereIn("id", categories)
                .execute(categories)
                .fetchAll()
            ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Category> findAll() {
        try {
            return this.select("*").execute().fetchAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Category> findAllBy(long movieId) {
        try {
            return this
                .select("c.*")
                .addAlias("c")
                .join("movies_categories mc", "mc.category_id = c.id")
                .where("mc.movie_id = ?")
                .execute(movieId)
                .fetchAll()
            ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String table() {
        return "categories";
    }

    @Override
    protected RowMapper<Category> mapper() {
        return new CategoriesMapper();
    }
}
