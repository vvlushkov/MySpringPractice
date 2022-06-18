package com.lushkov.springpractice.dao;

import java.util.List;

/**
 * Standard CRUD methods
 *
 * @param <E>
 * @author Victor Lushkov
 * @version 1.0
 */
public interface Dao<E> {
    void create(E entity);

    void update(E entity);

    void delete(E entity);

    List<E> findAll();

    E read(Long id);
}
