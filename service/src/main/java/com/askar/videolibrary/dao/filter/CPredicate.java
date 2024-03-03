package com.askar.videolibrary.dao.filter;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;

import java.util.ArrayList;
import java.util.List;

public class CPredicate {

    private final List<Predicate> predicates = new ArrayList<>();

    public static CPredicate builder() {
        return new CPredicate();
    }

    public <T> CPredicate add(CriteriaBuilder cb,Expression<T> object2, T object) {
        if (object != null) {
            predicates.add((Predicate) cb.equal(object2,object));
        }
        return this;
    }

    public Predicate buildOr() {
        return ExpressionUtils.anyOf(predicates);
    }
}
