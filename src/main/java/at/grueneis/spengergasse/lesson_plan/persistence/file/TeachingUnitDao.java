/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.persistence.file;

import java.io.File;

import at.grueneis.spengergasse.lesson_plan.domain.TeachingUnit;

public class TeachingUnitDao extends AbstractStreamDao<TeachingUnit> {

    private static final String FILE_NAME = "teachingUnits";

    private final String directoryName;

    public TeachingUnitDao(String directoryName) {
        this.directoryName = directoryName;
    }

    @Override
    public String fileName() {
        return directoryName + File.separator + FILE_NAME;
    }
}
