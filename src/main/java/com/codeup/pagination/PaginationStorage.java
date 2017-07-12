/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.pagination;

import java.util.List;

public interface PaginationStorage <T> {
    long itemsCount();
    List<T> slice(int offset, int length);
}
