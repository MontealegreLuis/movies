/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.statements;

import com.codeup.db.builders.queries.Select;

public interface Criteria {
    void applyTo(Select select);
}
