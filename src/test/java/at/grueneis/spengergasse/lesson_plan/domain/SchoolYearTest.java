/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.domain;

import java.util.Date;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class SchoolYearTest {
    @Test
    public void modifyDateOfSchoolYear() {
        // given
        Date start = new Date();
        SchoolYear schoolYear = new SchoolYear("hugo", (Date) start.clone(),
                new Date());

        // when
        schoolYear.getStart().setTime(0);

        // then
        assertThat(schoolYear.getStart(), is(equalTo(start)));
    }
}
