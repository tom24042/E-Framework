/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.persistence.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractStreamDao<T> implements StreamDao<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public abstract String fileName();

    @SuppressWarnings("unchecked")
    @Override
    public Iterable<T> readAll() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream(fileName()))) {
            return (Iterable<T>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.warn("Failed to read objects from {} with exception {}",
                    fileName(), e);
            throw new LessonPlanPersistenceException("Failed to read objects.",
                    e);
        }
    }

    @Override
    public void writeAll(Iterable<T> objects) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(fileName()))) {
            objectOutputStream.writeObject(objects);
        } catch (IOException e) {
            logger.warn("Failed to write objects {} to {} with exception {}",
                    objects, fileName(), e);
            throw new LessonPlanPersistenceException(
                    "Failed to persist objects.", e);
        }
    }
}
