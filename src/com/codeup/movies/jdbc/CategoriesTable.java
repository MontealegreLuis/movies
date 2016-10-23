/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.jdbc;

import com.codeup.db.RowMapper;
import com.codeup.db.Table;
import com.codeup.movies.Category;

import java.sql.Connection;
import java.util.List;

class CategoriesTable extends Table<Category> {
    CategoriesTable(Connection connection) {
        super(connection);
    }

    Category insert(String name) {
        return this.createInsert("name").execute(name).fetch();
    }

    Category findBy(String name) {
        return this.select("*").where("name = ?").execute(name).fetch();
    }

    List<Category> findAllIn(String... categories) {
        return this
            .select("*")
            .whereIn("id", categories)
            .execute(categories)
            .fetchAll()
        ;
    }

    List<Category> findAll() {
        return this.select("*").execute().fetchAll();
    }

    List<Category> findAllBy(long movieId) {
        return this
            .select("c.*")
            .addAlias("c")
            .join("movies_categories mc", "mc.category_id = c.id")
            .where("mc.movie_id = ?")
            .execute(movieId)
            .fetchAll()
        ;
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
