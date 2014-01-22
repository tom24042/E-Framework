/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.domain;

import java.io.Serializable;
import java.util.UUID;

public class TeachingUnit implements Serializable {

    private static final long serialVersionUID = -8191255976454225754L;

    private final UUID uuid;
    private final String name;

    public TeachingUnit(String name) {
        uuid = UUID.randomUUID();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
        TeachingUnit other = (TeachingUnit) obj;
        if (!uuid.equals(other.uuid))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TeachingUnit [uuid=" + uuid + "]";
    }
}
