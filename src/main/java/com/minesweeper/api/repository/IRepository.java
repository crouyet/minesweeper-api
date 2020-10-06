package com.minesweeper.api.repository;

import java.util.Map;
import java.util.Optional;

public interface IRepository<T> {
    void save(T o);
    Map<String,T> findAll();
    Optional<T> findById(String id);
    void update(T o);
    void delete(String id);
}