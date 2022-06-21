package com.lushkov.springpractice.services;


import java.util.List;

public interface Service<E> {

    void create(E entity);

    void update(E entity);

    void delete(E entity);

    List<E> findAll();

    E read(Long id);
}
