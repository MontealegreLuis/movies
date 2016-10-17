/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.statements;

import com.codeup.db.builders.queries.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompoundCriteria extends Criteria {
    private final List<Criteria> criterias = new ArrayList<>();

    public CompoundCriteria(Criteria... criterias) {
        Collections.addAll(this.criterias, criterias);
    }

    @Override
    public void applyTo(Select select) {
        criterias.forEach(criteria -> {
            criteria.applyTo(select);
            arguments().addAll(criteria.arguments());
        });
    }
}
