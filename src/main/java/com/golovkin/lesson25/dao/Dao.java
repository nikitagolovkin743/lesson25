package com.golovkin.lesson25.dao;

import com.golovkin.lesson25.model.Identifiable;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Identifiable> {
    List<T> findAll();

    Long create(T t);

    Optional<T> get(Long id);

    void update(T t, Long id);

    void delete(Long id);
}
