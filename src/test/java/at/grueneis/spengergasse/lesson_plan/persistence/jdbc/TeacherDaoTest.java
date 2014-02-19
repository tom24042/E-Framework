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

	private List<Teacher> teachers;
	
	private long insertedTeacherId;

    @Before
    public void setup() throws Exception {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        teacherDao = new TeacherDao(connection);
        Statement statement = connection.createStatement();
        //TODO: Objekt erstellen, hash berechnen und in db schreiben
        statement
                .execute("CREATE TABLE teachers (id number, firstname varchar, lastname varchar, birthdate date, email varchar, md5Hash varchar)");
//        statement
//                .execute("INSERT INTO teachers (id, firstname, lastname, birthdate, email, md5Hash) VALUES (1, 'Joachim', 'Grueneis', '2013-11-28', 'grueneis@speangergasse.at', 'abcd')");
        Teacher t = new Teacher("Joachim", "Grueneis", new Date(2013,11,28), "grueneis@speangergasse.at");
        teacherDao.save(t);
        insertedTeacherId = t.getId();
//        statement
//                .execute("INSERT INTO teachers (id, firstname, lastname, birthdate, email, md5Hash) VALUES (1, 'Joachim', 'Grueneis', '2013-11-28', 'grueneis@speangergasse.at', 'abcd')");
    }

    @After
    public void teardown() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
    
    @Test
    public void testInsert() {
        Teacher teacher = new Teacher("Andreas", "Resch", new Date(), "resch@spengergasse.at");
        teacherDao.save(teacher);
        insertedTeacherId = teacher.getId();
        Assert.assertNotNull(teacher.getId());
        Assert.assertNotNull(teacher.getMd5Hash());
    }

    @Test
    public void testFindAll() {
        teachers = teacherDao.findAll();
        Assert.assertNotNull(teachers);
        Assert.assertEquals(1, teachers.size());
    }

    @Test
    public void testFindById() {
        Teacher teacher = teacherDao.findById(insertedTeacherId);
        Assert.assertNotNull(teacher);
        Assert.assertEquals(Long.valueOf(insertedTeacherId), teacher.getId());
        Assert.assertNotNull(teacher.getMd5Hash());
    }

   

    @Test (expected=LessonPlanDataAccessException.class)
    public void testUpdateChangedInDB() {
        Teacher teacher = new Teacher("Hans", "Müller", new Date(1920, 1, 1), "mueller@spengergasse.at");
        teacherDao.save(teacher);
        Long teacherId = teacher.getId();
        Teacher teacher2 = teacherDao.findById(teacherId);
        teacher.setBirthdate(new Date(1930, 1, 1));
        teacherDao.save(teacher);
        teacher2.setBirthdate(new Date(1910, 1, 2));
        teacherDao.save(teacher2);
    }
    
    @Test
    public void testUpdate() {
        Teacher teacher = new Teacher("Hans", "Müller", new Date(1920, 1, 1), "mueller@spengergasse.at");
        teacherDao.save(teacher);
        long teacherId = teacher.getId();
        teacher = teacherDao.findById(teacherId);
        teacher.setBirthdate(new Date(1910, 1, 1));
        teacherDao.save(teacher);
        Assert.assertNotNull(teacher.getId());
    }

    @Test
    public void testDelete() {
        Teacher teacher = teacherDao.findById(insertedTeacherId);
        teacherDao.delete(teacher);
		Assert.assertTrue(teacherDao.findAll().size() == 0);
	}
}
