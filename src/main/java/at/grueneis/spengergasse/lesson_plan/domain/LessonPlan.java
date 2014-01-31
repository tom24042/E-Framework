/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LessonPlan extends BasePersistable {
    private final String name;
    private final SchoolYear schoolYear;
    private final List<Lesson> lessons;

    public LessonPlan(String name, SchoolYear schoolYear,
            Collection<Lesson> lessons) {
        this.name = name;
        this.schoolYear = schoolYear;
        this.lessons = new ArrayList<>();
        for (Lesson lesson : lessons)
            this.lessons.add(lesson);
    }

    public Collection<Lesson> getLessons() {
        // Collection<Lesson> returnList = new ArrayList<>();
        // for (Lesson lesson : this.lessons)
        // returnList.add(lesson);
        // return returnList;
        return Collections.unmodifiableCollection(lessons);
    }

	@Override
	public String[] getAllAttributesAsString() {
//		private final String name;
//	    private final SchoolYear schoolYear;
//	    private final List<Lesson> lessons;
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(name);
		arrayList.add(schoolYear.calculateMd5Hash());
		for(Lesson l : lessons) arrayList.add(l.calculateMd5Hash());
		
		return (String[]) arrayList.toArray();
	}
}
