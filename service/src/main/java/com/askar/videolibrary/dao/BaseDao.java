package com.askar.videolibrary.dao;

import com.askar.videolibrary.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseDao<K extends Serializable, E extends BaseEntity<K>> implements Dao<K, E> {

    private final Class<E> clazz;
    private final EntityManager entityManager;

    public E save(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    public void delete(E entity) {
        entityManager.remove(entity);
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<E> findById(K id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @Override
    public List<E> findAll() {
        var criteria = entityManager.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);
        return entityManager.createQuery(criteria)
                .getResultList();
    }

}
