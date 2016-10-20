/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies;

public class Category {
    private final long id;
    private final String name;

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public static Category named(String category) {
        return new Category(0L, category);
    }
}
