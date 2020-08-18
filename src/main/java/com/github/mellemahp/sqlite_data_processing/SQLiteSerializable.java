package com.github.mellemahp.sqlite_data_processing;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mellemahp.sqlite_data_processing.annotations.SQLiteField;

import org.apache.commons.lang3.reflect.FieldUtils;

public interface SQLiteSerializable {

    default String getSqlStatement() {
        return String.format(
                "INSERT INTO %s(%s) VALUES(%s)",
                getTableName(),
                getPropertyListString(),
                getValueFillerString());
    }

    default String getPropertyListString() {
        List<Field> fields = getAnnotatedFields();
        return convertFieldsToString(fields);
    }

    default List<Field> getAnnotatedFields() {
        return FieldUtils.getFieldsListWithAnnotation(
                this.getClass(),
                SQLiteField.class);
    }

    default String convertFieldsToString(List<Field> fields) {
        return fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(","));
    }

    default String getValueFillerString() {
        return getAnnotatedFields().stream()
                .map(f -> "?")
                .collect(Collectors.joining(","));
    }

    public default String getTableName() {
        return this.getClass()
                .getSimpleName()
                .replace("DataContainer", "")
                .toLowerCase();
    }

    default void fillPreparedStatement(PreparedStatement preparedStatement) throws SQLException,
            IllegalAccessException, JsonProcessingException {
        List<Field> fields = getAnnotatedFields();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            int statementIndex = i + 1;

            SQLiteField annotation = field.getAnnotation(SQLiteField.class);
            SQLiteType sqlType = annotation.type();

            Object value = field.get(this);
            if (annotation.json()) {
                value = convertToJson(value);
            }

            setValueForPreparedStatement(preparedStatement, sqlType, statementIndex, value);
        }
    }

    default void setValueForPreparedStatement(PreparedStatement preparedStatement,
            SQLiteType sqlType, int statementIndex, Object value) throws SQLException {
        Class<?> valueType = sqlType.getClass();
        Method method = sqlType.getFillMethod();
        try {
            method.invoke(preparedStatement, statementIndex, valueType.cast(value));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    default String convertToJson(Object value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(value);
    }

    public default PreparedStatement getPreparedStatement(Connection connection)
            throws SQLException {
        String sqliteString = getSqlStatement();
        return connection.prepareStatement(sqliteString);
    }
}