/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private long id;
    private String title;
    private int rating;
    private String thumbnail;
    private List<Category> categories = new ArrayList<>();

    public Movie(long id, String title, int rating, String thumbnail) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.thumbnail = thumbnail;
    }

    private Movie(String title, Category category) {
        this(0L, title, 0, null); // No ID, no rating and no thumbnail
        categories.add(category);
    }

    public Movie(
        String title,
        int rating,
        String thumbnail,
        List<Category> categories
    ) {
        this(0L, title, rating, thumbnail);
        this.categories = categories;
    }

    public void rate(int rate) {
        rating = rate;
    }

    public String title() {
        return title;
    }

    public long id() {
        return id;
    }

    public int rating() {
        return rating;
    }

    public String thumbnail() {
        return thumbnail;
    }

    public static Movie titled(String title, Category category) {
        return new Movie(title, category);
    }

    public List<Category> categories() {
        return categories;
    }

    public Movie addCategories(List<Category> categories) {
        this.categories = categories;
        return this;
    }
}
