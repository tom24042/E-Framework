/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.persistence.jdbc;

import at.grueneis.spengergasse.lesson_plan.domain.BasePersistable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * "template pattern"
 */
public abstract class AbstractDatabaseDao<T extends BasePersistable> implements DatabaseDao<T> {

    private final Connection connection;

    private PreparedStatement findAllStatement;

    private PreparedStatement findByIdStatement;

    private PreparedStatement countStatement;

    private PreparedStatement insertStatement;

    private PreparedStatement updateStatement;

    private PreparedStatement deleteStatement;

    public AbstractDatabaseDao(Connection connection) {
        this.connection = connection;
    }

    protected Connection connection() {
        try {
            if (connection == null) {
                throw new IllegalStateException("Connection must not be null");
            }
            if (connection.isClosed()) {
                throw new IllegalStateException("Connection must not be closed");
            }
        } catch (SQLException e) {
            throw new LessonPlanDataAccessException("Failed to validate connection", e);
        }
        return connection;
    }

    private PreparedStatement findAllStatement() {
        try {
            if (findAllStatement == null) {
                StringBuffer statementText = new StringBuffer();
                statementText.append("SELECT ");
                statementText.append(columnList());
                statementText.append(" FROM ");
                statementText.append(tableName());
                System.out.println("Creating SQL statement: " + statementText);
                findAllStatement = connection().prepareStatement(statementText.toString());
            }
            return findAllStatement;

        } catch (SQLException e) {
            throw new LessonPlanDataAccessException("Failed to create find all statement", e);
        }
    }

    private PreparedStatement findByIdStatement() {
        try {
            if (findByIdStatement == null) {
                StringBuffer statementText = new StringBuffer();
                statementText.append("SELECT ");
                statementText.append(columnList());
                statementText.append(" FROM ");
                statementText.append(tableName());
                statementText.append(" WHERE ");
                statementText.append(idColumnName()).append(" = ? ");
                System.out.println("Creating SQL statement: " + statementText);
                findByIdStatement = connection().prepareStatement(statementText.toString());
            }
            return findByIdStatement;

        } catch (SQLException e) {
            throw new LessonPlanDataAccessException("Failed to create find by id statement", e);
        }
    }

    private PreparedStatement insertStatement() {
        try {
            if (insertStatement == null) {
                StringBuffer statementText = new StringBuffer();
                statementText.append("INSERT INTO ").append(tableName()).append(" ( ");
                for (String columnName : otherColumnNames()) {
                    statementText.append(columnName).append(" , ");
                }
                statementText.append(idColumnName());
                statementText.append(" ) ");
                statementText.append(" VALUES ( ");
                for (String columnName : otherColumnNames()) {
                    statementText.append(" ?, ");
                }
                statementText.append(" ? "); // id column
                statementText.append(" ) ");
                System.out.println("Creating SQL statement: " + statementText);
                insertStatement = connection().prepareStatement(statementText.toString());
            }
            return insertStatement;
        } catch (SQLException e) {
            throw new LessonPlanDataAccessException("Failed to create insert statement", e);
        }
    }

    private PreparedStatement updateStatement() {
        try {
            if (updateStatement == null) {
                StringBuffer statementText = new StringBuffer();
                statementText.append("UPDATE ").append(tableName());
                statementText.append(" SET ");
                int count = 0;
                for (String columnName : otherColumnNames()) {
                    count++;
                    statementText.append(" ").append(columnName).append(" = ? ");
                    if (count < otherColumnNames().length) {
                        statementText.append(" , ");
                    }
                }
                statementText.append(" WHERE ").append(idColumnName()).append(" = ? ");
                System.out.println("Creating SQL statement: " + statementText);
                updateStatement = connection().prepareStatement(statementText.toString());
            }
            return updateStatement;
        } catch (SQLException e) {
            throw new LessonPlanDataAccessException("Failed to create update statement", e);
        }
    }

    private PreparedStatement deleteStatement() {
        try {
            if (deleteStatement == null) {
                StringBuffer statementText = new StringBuffer();
                statementText.append("DELETE FROM ").append(tableName());
                statementText.append(" WHERE ").append(idColumnName()).append(" = ? ");
                System.out.println("Creating SQL statement: " + statementText);
                deleteStatement = connection().prepareStatement(statementText.toString());
            }
            return deleteStatement;
        } catch (SQLException e) {
            throw new LessonPlanDataAccessException("Failed to create delete statement", e);
        }
    }

    private PreparedStatement countStatement() {
        try {
            if (countStatement == null) {
                StringBuffer statementText = new StringBuffer();
                statementText.append("SELECT COUNT(*) FROM ").append(tableName());
                System.out.println("Creating SQL statement: " + statementText);
                deleteStatement = connection().prepareStatement(statementText.toString());
            }
            return countStatement;
        }
        catch (SQLException e) {
            throw new LessonPlanDataAccessException("Failed to create count statement", e);
        }
    }

    protected abstract String idColumnName();

    protected abstract String[] otherColumnNames();

    private String columnList() {
        StringBuffer columnList = new StringBuffer();
        columnList.append(idColumnName());
        for (String columnName : otherColumnNames()) {
            columnList.append(", ").append(columnName);
        }
        return columnList.toString();
    }

    protected abstract String tableName();

    protected abstract T bind(ResultSet resultSet);

    protected abstract void setValuesOfOtherColumnsIntoStatment(PreparedStatement preparedStatement, T entity);

    @Override
    public final List<T> findAll() {
        try {
            List<T> entities = new ArrayList<>();
            PreparedStatement statement = findAllStatement();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T entity = bind(resultSet);
                entities.add(entity);
            }
            resultSet.close();
            return entities;
        } catch (SQLException e) {
            throw new LessonPlanDataAccessException("Failed to fetch all entities", e);
        }
    }

    @Override
    public final T findById(Long id) {
        try {
            PreparedStatement findByIdStatement = findByIdStatement();
            findByIdStatement.setLong(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            if (!resultSet.next()) {
                throw new LessonPlanDataAccessException("Exact match didn't return data");
            }
            T entity = bind(resultSet);
            if (resultSet.next()) {
                throw new LessonPlanDataAccessException("Exact returned more then one row");
            }
            return entity;
        } catch (SQLException e) {
            throw new LessonPlanDataAccessException("Failed at findById query", e);
        }
    }

    @Override
    public void save(T t) {
        if (t.getId() == null) {
        	t.updateMd5Hash();
            insert(t);
        } else {
        	T fromDB = findById(t.getId());
        	if(!t.getMd5Hash().equals(fromDB.getMd5Hash())){
        		throw new LessonPlanDataAccessException("Could not update in DB because object has been changed in DB since last loading!");
        	}
        	t.updateMd5Hash();
            update(t);
        }
    }

    private void insert(T t) {
        try {
            PreparedStatement insertStmnt = insertStatement();
            Long newId = generateNewId();
            t.setId(newId);
            setValuesOfOtherColumnsIntoStatment(insertStmnt, t);
            insertStmnt.setLong(otherColumnNames().length + 1, t.getId());
            int effectedRowCount = insertStmnt.executeUpdate();
            if (effectedRowCount == 0) {
                throw  new LessonPlanDataAccessException("Insert did not insert a row.");
            } else if (effectedRowCount != 1) {
                throw  new LessonPlanDataAccessException("Insert inserted more then one row.");
            }
        } catch (SQLException e) {
            throw new LessonPlanDataAccessException("Failed at insert", e);
        }
    }

    private void update(T t) {
        try {
            PreparedStatement updateStmnt = updateStatement();
            setValuesOfOtherColumnsIntoStatment(updateStmnt, t);
            updateStmnt.setLong(otherColumnNames().length + 1, t.getId());
            int effectedRowCount = updateStmnt.executeUpdate();
            if (effectedRowCount == 0) {
                throw  new LessonPlanDataAccessException("Update did not find a matching row.");
            } else if (effectedRowCount != 1) {
                throw  new LessonPlanDataAccessException("Update found more then one row to delete.");
            }
        } catch (SQLException e) {
            throw new LessonPlanDataAccessException("Failed at update", e);
        }
    }

    @Override
    public void delete(T t) {
        delete(t.getId());
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement deleteStmnt = deleteStatement();
            deleteStmnt.setLong(1, id);
            int effectedRowCount = deleteStmnt.executeUpdate();
            if (effectedRowCount == 0) {
                throw  new LessonPlanDataAccessException("Delete did not find a matching row.");
            } else if (effectedRowCount != 1) {
                throw  new LessonPlanDataAccessException("Exact delete found more then one row to delete.");
            }
        } catch (SQLException e) {
            throw new LessonPlanDataAccessException("Failed at delete", e);
        }
    }

    @Override
    public Long count() {
        try {
            PreparedStatement countStatement = countStatement();
            ResultSet resultSet = countStatement.executeQuery();
            if (!resultSet.next()) {
                throw new LessonPlanDataAccessException("Count didn't return data");
            }
            Long count = resultSet.getLong(1);
            if (resultSet.next()) {
                throw new LessonPlanDataAccessException("Count returned more then one row");
            }
            return count;
        } catch (SQLException e) {
            throw new LessonPlanDataAccessException("Failed at count query", e);
        }
    }

    private Long generateNewId() {
        return new Random().nextLong();
    }
}
