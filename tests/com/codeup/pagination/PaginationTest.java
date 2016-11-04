/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.pagination;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaginationTest {
    private PaginationStorage<Object> storage;
    private int pageSize;

    @Before
    public void createStorage() {
        storage = mock(PaginationStorage.class);
        pageSize = 10;
    }

    @Test
    public void it_knows_when_there_is_no_pages_to_show() {
        int page = 1;
        when(storage.itemsCount()).thenReturn(0L);
        Pagination<Object> pagination = new Pagination<>(pageSize, storage, page);

        assertFalse(pagination.hasPages());
    }

    @Test
    public void it_calculates_number_of_pages() {
        int page = 1;
        when(storage.itemsCount()).thenReturn(23L);
        Pagination<Object> pagination = new Pagination<>(pageSize, storage, page);

        assertEquals(3, pagination.pagesCount());
    }

    @Test
    public void it_knows_when_it_is_the_final_page() {
        int page = 3;
        when(storage.itemsCount()).thenReturn(23L);
        Pagination<Object> pagination = new Pagination<>(pageSize, storage, page);

        assertFalse(pagination.hasNextPage());
    }

    @Test
    public void it_knows_when_it_is_the_first_page() {
        int page = 1;
        when(storage.itemsCount()).thenReturn(23L);
        Pagination<Object> pagination = new Pagination<>(pageSize, storage, page);

        assertFalse(pagination.hasPreviousPage());
    }

    @Test
    public void it_knows_the_next_page() {
        int page = 2;
        when(storage.itemsCount()).thenReturn(23L);
        Pagination<Object> pagination = new Pagination<>(pageSize, storage, page);

        assertEquals(3, pagination.nextPage());
    }

    @Test
    public void it_knows_the_previous_page() {
        int page = 3;
        when(storage.itemsCount()).thenReturn(23L);
        Pagination<Object> pagination = new Pagination<>(pageSize, storage, page);

        assertEquals(2, pagination.previousPage());
    }

    @Test
    public void it_normalize_the_page_value_if_its_bigger_than_the_count_of_pages() {
        int page = 100;
        int offset = 20;
        List<Object> items = new ArrayList<>(
            Arrays.asList(new Object[]{new Object(), new Object(), new Object()})
        );
        when(storage.itemsCount()).thenReturn(23L);
        when(storage.slice(offset, pageSize)).thenReturn(items);
        Pagination<Object> pagination = new Pagination<>(pageSize, storage, page);

        assertFalse(pagination.hasNextPage());
        assertEquals(2, pagination.previousPage());
        assertEquals(3, pagination.pageItems().size());
    }

    @Test
    public void it_normalizes_the_page_value_if_its_negative_or_zero() {
        int page = -2;
        when(storage.itemsCount()).thenReturn(23L);
        Pagination<Object> pagination = new Pagination<>(pageSize, storage, page);

        assertFalse(pagination.hasPreviousPage());
        assertEquals(2, pagination.nextPage());
    }
}
