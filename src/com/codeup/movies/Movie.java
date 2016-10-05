/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int id;
    private String title;
    private int rating;
    private List<Category> categories = new ArrayList<>();

    public Movie(int id, String title, int rating) {
        this.id = id;
        this.title = title;
        this.rating = rating;
    }

    private Movie(String title, Category category) {
        this(0, title, 0);
        categories.add(category);
    }

    public Movie(String title, int rating, List<Category> categories) {
        this(0, title, rating);
        this.categories = categories;
    }

    public void rate(int rate) {
        rating = rate;
    }

    public String title() {
        return title;
    }

    public int id() {
        return id;
    }

    public int rating() {
        return rating;
    }

    public static Movie titled(String title, Category category) {
        return new Movie(title, category);
    }

    public List<Category> categories() {
        return categories;
    }

    public boolean isCategorized() {
        return !categories.isEmpty();
    }

    public Movie addCategories(List<Category> categories) {
        this.categories = categories;
        return this;
    }
}
