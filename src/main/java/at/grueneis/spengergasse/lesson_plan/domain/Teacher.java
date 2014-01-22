/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.domain;

import java.io.Serializable;
import java.util.Date;

public class Teacher extends BasePersistable implements Serializable {
    private static final long serialVersionUID = 2052070897330839212L;

    private final String firstname;
    private final String lastname;
    private Date birthdate;
    private final String email;

    public Teacher(Long id, String firstname, String lastname, Date birthdate,
            String email) {
        this.setId(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.email = email;
    }

    public Teacher(String firstname, String lastname, Date birthdate,
            String email) {
        this(null, firstname, lastname, birthdate, email);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return String.format("%s %s", firstname, lastname);
    }

    public Date getBirthdate() {
        return (Date) birthdate.clone();
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + getId() + "firstname="
                + firstname + ", lastname=" + lastname + ", birthdate="
                + birthdate + ", email=" + email + "]";
    }
}
