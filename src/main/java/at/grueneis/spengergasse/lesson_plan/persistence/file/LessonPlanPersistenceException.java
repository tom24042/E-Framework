/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.persistence.file;

public class LessonPlanPersistenceException extends RuntimeException {

    private static final long serialVersionUID = -7569734749768568401L;

    public LessonPlanPersistenceException(String message) {
        super(message);
    }

    public LessonPlanPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
