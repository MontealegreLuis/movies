/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies;

import java.util.List;

public interface Categories {
    List<Category> all();
    Category named(String name);
    void add(Category category);
    List<Category> in(String... categories);
    List<Category> relatedTo(Movie movie);
}
