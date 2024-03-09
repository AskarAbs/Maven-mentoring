package com.askar.videolibrary.repository.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CPredicate {

    private final List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

    public static CPredicate builder() {
        return new CPredicate();
    }

    public <T> CPredicate add(CriteriaBuilder cb,Expression<T> object2, T object) {
        if (object != null) {
            predicates.add(cb.equal(object2,object));
        }
        return this;
    }

    public Predicate buildOr() {

        Predicate rv = null;

        for (Predicate b : predicates) {
            if (b != null) {
                rv = rv == null ? b : rv;
            }
        }

        return rv;
    }

}
