/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.persistence.jdbc;

public class LessonPlanDataAccessException extends RuntimeException {

    private static final long serialVersionUID = -3833144797242455841L;

    public LessonPlanDataAccessException(String message) {
        super(message);
    }

    public LessonPlanDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
