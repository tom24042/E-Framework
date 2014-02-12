/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.$;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.is;

@RunWith(JUnitParamsRunner.class)
public class WhenCreatingALesson {
    private TeachingUnit teachingUnit;
    private Teacher teacher;
    private SchoolClass schoolClass;
    private ClassRoom classRoom;

    @Before
    public void setup() {
        teachingUnit = new TeachingUnit("POS");
        teacher = new Teacher("Joachim", "Grüneis", null,
                "grueneis@spengergasse.at");
        schoolClass = new SchoolClass("4AHIF");
        classRoom = new ClassRoom("C4.08", "4", "C");
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters
    public void constructWithNull(TeachingUnit teachingUnit, Teacher teacher,
            SchoolClass schoolClass, ClassRoom classRoom) {
        new Lesson(teachingUnit, teacher, schoolClass, classRoom);
    }

    @SuppressWarnings("unused")
    private Object[] parametersForConstructWithNull() {
        TeachingUnit teachingUnit = new TeachingUnit("POS");
        Teacher teacher = new Teacher("Joachim", "Grüneis", null,
                "grueneis@spengergasse.at");
        SchoolClass schoolClass = new SchoolClass("4AHIF");
        ClassRoom classRoom = new ClassRoom("C4.08", "4", "C");
        return $(//
                $(null, null, null, null), //
                $(null, teacher, schoolClass, classRoom), //
                $(teachingUnit, null, schoolClass, classRoom), //
                $(teachingUnit, teacher, null, classRoom), //
                $(teachingUnit, teacher, schoolClass, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void aLessonNeedsTeachinUnit() {
        new Lesson(null, teacher, schoolClass, classRoom);
    }

    @Test(expected = IllegalArgumentException.class)
    public void aLessonNeedsTeacher() {
        new Lesson(teachingUnit, null, schoolClass, classRoom);
    }

    @Test(expected = IllegalArgumentException.class)
    public void aLessonNeedsSchoolClass() {
        new Lesson(teachingUnit, teacher, null, classRoom);
    }

    @Test(expected = IllegalArgumentException.class)
    public void aLessonNeedsClassRoom() {
        new Lesson(teachingUnit, teacher, schoolClass, null);
    }

    @Test
    public void aLessonNeedsTeacherRoomUnit() {
        // given
        // when
        Lesson lesson = new Lesson(teachingUnit, teacher, schoolClass,
                classRoom);
        // then
        assertThat(lesson.getTeacher(), is(teacher));
    }

    @Test
    public void twoLessonsAreEqualIfAllAttributesAreEqual() {
        // given
        // when
        Lesson lesson1 = new Lesson(teachingUnit, teacher, schoolClass,
                classRoom);
        Lesson lesson2 = new Lesson(teachingUnit, teacher, schoolClass,
                classRoom);
        // then
        assertThat(lesson1, is(lesson2));
    }
}
