/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.persistence.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JdbcPersistenceSpike {

    private Connection connection;

    @Before
    public void setup() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        Statement statement = connection.createStatement();
        statement
                .execute("CREATE TABLE teachers (id number, firstname varchar, lastname varchar)");
        statement
                .execute("INSERT INTO teachers (id, firstname, lastname) VALUES (1, 'Joachim', 'Grueneis')");
    }

    @After
    public void teardown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void aFindAll() {
        try {
            List<Teacher> entities = new ArrayList<>();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT id, firstname, lastname FROM teachers");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                entities.add(new Teacher(id, firstname, lastname));
            }
            resultSet.close();
            Assert.assertEquals(1, entities.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void aFindById() {
        try {
            String value = null;
            PreparedStatement statement = connection
                    .prepareStatement("SELECT id, firstname, lastname FROM teachers WHERE id = ?");
            statement.setLong(1, 1l);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                // not found exception
                Assert.fail("not found");
            } else {
                value = resultSet.getString("lastname");
            }
            if (resultSet.next()) {
                // too many entries!
                Assert.fail("too many rows");
            }
            resultSet.close();
            Assert.assertEquals("Grueneis", value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insert() {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO teachers (id, name) VALUES (?, ?)");
            statement.setLong(1, 2l);
            statement.setString(2, "Resch");
            int numberOfInsertedRecords = statement.executeUpdate();
            if (numberOfInsertedRecords != 1) {
                Assert.fail("insert failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE teachers SET name = ? WHERE id = ?");
            statement.setString(1, "Joachim");
            statement.setLong(2, 1l);
            int numberOfUpdatedRecords = statement.executeUpdate();
            if (numberOfUpdatedRecords != 1) {
                Assert.fail("update failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM teachers WHERE id = ?");
            statement.setLong(1, 1l);
            int numberOfDeletedRecords = statement.executeUpdate();
            if (numberOfDeletedRecords != 1) {
                Assert.fail("delete failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void countAll() {
        try {
            Long count = null;
            PreparedStatement statement = connection
                    .prepareStatement("SELECT count(*) FROM teachers");
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                // not found exception
                Assert.fail("not found");
            } else {
                count = resultSet.getLong(1);
            }
            if (resultSet.next()) {
                // too many entries!
                Assert.fail("too many rows");
            }
            resultSet.close();
            Assert.assertEquals(Long.valueOf(1l), count);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static class Teacher {

        private final Long id;

        private final String firstname;

        private final String lastname;

        public Teacher(Long id, String firstname, String lastname) {
            this.id = id;
            this.firstname = firstname;
            this.lastname = lastname;
        }

        public Long getId() {
            return id;
        }

        public String getFirstname() {
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }
    }
}
