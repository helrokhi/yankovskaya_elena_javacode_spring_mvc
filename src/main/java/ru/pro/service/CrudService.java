package ru.pro.service;

import java.util.List;
import java.util.UUID;

public interface CrudService<T, P> {
    T create(T t);

    List<P> findAll();

    P findById(UUID id);

    T update(UUID id, T t);

    void delete(UUID id);
}
