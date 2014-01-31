/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.domain;

public class ClassRoom extends BasePersistable{
    private final String name;
    private final String floor;
    private final String building;

    public ClassRoom(String name, String floor, String building) {
        this.name = name;
        this.floor = floor;
        this.building = building;
    }

    public String getName() {
        return name;
    }

    public String getFloor() {
        return floor;
    }

    public String getBuilding() {
        return building;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((building == null) ? 0 : building.hashCode());
        result = prime * result + ((floor == null) ? 0 : floor.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        ClassRoom other = (ClassRoom) obj;
        if (building == null) {
            if (other.building != null)
                return false;
        } else if (!building.equals(other.building))
            return false;
        if (floor == null) {
            if (other.floor != null)
                return false;
        } else if (!floor.equals(other.floor))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ClassRoom [name=" + name + ", floor=" + floor + ", building="
                + building + "]";
    }

	@Override
	public String[] getAllAttributesAsString() {
//		    private final String name;
//		    private final String floor;
//		    private final String building;
		return new String[]{
				name, floor, building
		};
	}
}
