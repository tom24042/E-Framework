/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.persistence.file;

import java.io.File;

import at.grueneis.spengergasse.lesson_plan.domain.Teacher;

public class TeacherDao extends AbstractStreamDao<Teacher> {

    private static final String FILE_NAME = "teachers";

    private final String directoryName;

    public TeacherDao(String directoryName) {
        this.directoryName = directoryName;
    }

    @Override
    public String fileName() {
        return directoryName + File.separator + FILE_NAME;
    }
}
