/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.persistence.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.grueneis.spengergasse.lesson_plan.domain.Teacher;

public class TeacherDaoTest {

    private Connection connection;

    private TeacherDao teacherDao;

    @Before
    public void setup() throws Exception {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        teacherDao = new TeacherDao(connection);
        Statement statement = connection.createStatement();
        statement
                .execute("CREATE TABLE teachers (id number, firstname varchar, lastname varchar, birthdate date, email varchar)");
        statement
                .execute("INSERT INTO teachers (id, firstname, lastname, birthdate, email) VALUES (1, 'Joachim', 'Grueneis', '2013-11-28', 'grueneis@speangergasse.at')");
    }

    @After
    public void teardown() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void testFindAll() {
        List<Teacher> teachers = teacherDao.findAll();
        Assert.assertNotNull(teachers);
        Assert.assertEquals(1, teachers.size());
    }

    @Test
    public void testFindById() {
        Teacher teacher = teacherDao.findById(1l);
        Assert.assertNotNull(teacher);
        Assert.assertEquals(Long.valueOf(1l), teacher.getId());
    }

    @Test
    public void testInsert() {
        Teacher teacher = new Teacher("Andreas", "Resch", new Date(), "resch@spengergasse.at");
        teacherDao.save(teacher);
        Assert.assertNotNull(teacher.getId());
    }

    @Test
    public void testUpdate() {
        Teacher teacher = teacherDao.findById(1l);
        teacher.setBirthdate(new Date());
        teacherDao.save(teacher);
        Assert.assertNotNull(teacher.getId());
    }

    @Test
    public void testDelete() {
        Teacher teacher = teacherDao.findById(1l);
        teacherDao.delete(teacher);
        Assert.assertNotNull(teacher.getId());
    }
}
