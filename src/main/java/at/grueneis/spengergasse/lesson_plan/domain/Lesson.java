/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.domain;

public class Lesson extends BasePersistable{

    private final TeachingUnit teachingUnit;
    private final Teacher teacher;
    private final ClassRoom classRoom;
    private final SchoolClass schoolClass;

    public Lesson(TeachingUnit teachingUnit, Teacher teacher,
            SchoolClass schoolClass, ClassRoom classRoom) {
        assertArgumentNotNull("teachingUnit", teachingUnit);
        assertArgumentNotNull("teacher", teacher);
        assertArgumentNotNull("schoolClass", schoolClass);
        assertArgumentNotNull("classRoom", classRoom);
        this.teachingUnit = teachingUnit;
        this.teacher = teacher;
        this.schoolClass = schoolClass;
        this.classRoom = classRoom;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public TeachingUnit getTeachingUnit() {
        return teachingUnit;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((classRoom == null) ? 0 : classRoom.hashCode());
        result = prime * result
                + ((schoolClass == null) ? 0 : schoolClass.hashCode());
        result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
        result = prime * result
                + ((teachingUnit == null) ? 0 : teachingUnit.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Lesson other = (Lesson) obj;
        if (classRoom == null) {
            if (other.classRoom != null)
                return false;
        } else if (!classRoom.equals(other.classRoom))
            return false;
        if (schoolClass == null) {
            if (other.schoolClass != null)
                return false;
        } else if (!schoolClass.equals(other.schoolClass))
            return false;
        if (teacher == null) {
            if (other.teacher != null)
                return false;
        } else if (!teacher.equals(other.teacher))
            return false;
        if (teachingUnit == null) {
            if (other.teachingUnit != null)
                return false;
        } else if (!teachingUnit.equals(other.teachingUnit))
            return false;
        return true;
    }

    public static void assertArgumentNotNull(String argumentName,
            Object argument) {
        if (argument == null)
            throw new IllegalArgumentException(argumentName
                    + " must not be null");
    }

	@Override
	public String[] getAllAttributesAsString() {
//		 	private final TeachingUnit teachingUnit;
//		    private final Teacher teacher;
//		    private final ClassRoom classRoom;
//		    private final SchoolClass schoolClass;
		return new String[]{
			""+teachingUnit.getId(),
			""+teacher.getId(),
			""+classRoom.getId(),
			""+schoolClass.getId()
		};
	}
}
