package com.codeup.movies.jdbc;
/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */

import com.codeup.movies.Category;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CategoriesMapperTest {
    @Test
    public void it_maps_correctly_a_category() {
        CategoriesMapper mapper = new CategoriesMapper();

        List<Object> values = new ArrayList<>();
        values.addAll(Arrays.asList(6L, "horror"));
        Category category = mapper.mapRow(values);

        assertEquals(6L, category.id());
        assertEquals("horror", category.name());
    }
}
