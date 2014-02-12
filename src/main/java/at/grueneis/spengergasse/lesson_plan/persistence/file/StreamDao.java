/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.persistence.file;

public interface StreamDao<T> {

    Iterable<T> readAll();

    void writeAll(Iterable<T> objects);
}
