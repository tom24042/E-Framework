/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.persistence.jdbc;

import java.util.List;

public interface DatabaseDao<T> {

    List<T> findAll();

    T findById(Long id);

    void save(T t);

    void delete(T t);

    void delete(Long id);

    Long count();
}
