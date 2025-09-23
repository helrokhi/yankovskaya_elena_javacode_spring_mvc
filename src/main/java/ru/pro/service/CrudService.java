package ru.pro.service;

import java.util.UUID;

public interface CrudService<T> {
    T create(T t);

    T findAll();

    T findById(UUID id);

    T update(UUID id, T t);

    void delete(UUID id);
}
