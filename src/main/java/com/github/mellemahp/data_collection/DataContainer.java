package com.github.mellemahp.data_collection;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.lang3.reflect.FieldUtils;
import java.lang.reflect.Method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface DataContainer {

    public DataContainer withConnection(Connection connection);

    public PreparedStatement toPreparedStatement();

    public UUID getSimulationID();

    default String getSqlStatement() {
        return String.format(
                "INSERT INTO {%s}({%s}) VALUES({%s})",
                getTableName(),
                getPropertyListString(),
                getValueFillerString());
    }

    default List<Field> getAnnotatedFields() {
        return FieldUtils.getFieldsListWithAnnotation(
                this.getClass(),
                SQLiteField.class);
    }

    default List<String> convertFieldsToStrings(List<Field> fields) {
        return fields.stream()
                .map(Field::toString)
                .collect(Collectors.toList());
    }

    default String getPropertyListString() {
        List<Field> fields = getAnnotatedFields();
        List<String> fieldStrings = convertFieldsToStrings(fields);
        return String.join(",", fieldStrings);
    }

    default String getSQLColumnDefinitions() { 
        List<Field> fields = getAnnotatedFields();
        List<String> columns = new ArrayList<>();
        for (Field field: fields) { 
            List<String> column = new ArrayList<>();
            SQLiteField annotation = field.getAnnotation(SQLiteField.class);

            column.add(field.toString());
            column.add(annotation.type().toString()); 
            if (field.isAnnotationPresent(PrimarySQLKey.class)) { 
                column.add("PRIMARY KEY");
            } else if (annotation.nonNull()) { 
                column.add("NOT NULL");
            }

            String columnString = String.join(" ", column);
            columns.add(columnString);
        }
        return String.join(",", columns);
    }

    default String getValueFillerString() {
        List<Field> fields = getAnnotatedFields();
        List<String> fillerStrings = fields.stream()
            .map(f -> "?")
            .collect(Collectors.toList());

        return String.join(",", fillerStrings);
    }

    default String getTableName() {
        return this.getClass()
            .getSimpleName()
            .split("Container")[0]
            .toLowerCase();
    }

    public default String getTableDefinition() { 
        return String.format("CREATE TABLE IF NOT EXISTS {%s}({%s});",
            getTableName(), 
            getSQLColumnDefinitions()
        );
    }
    
    default PreparedStatement fillPreparedStatement(PreparedStatement preparedStatement) { 
        List<Field> fields = getAnnotatedFields();

        int fieldNum = 1;
        for (Field field: fields) {
            SQLiteField annotation = field.getAnnotation(SQLiteField.class);
            SQLiteTypes sqlType = annotation.type();
            
            Object value;
            try { 
                value = field.get(sqlType.getType());
            } catch (IllegalAccessException e) {

            }
            if (annotation.json()) { 
                value = convertToJson(value);
            }
            Method setter = getSetterForPreparedStatement(preparedStatement, sqlType);
            setter.invoke(fieldNum, value);
        }
        
    }

    default Method getSetterForPreparedStatement(PreparedStatement preparedStatement, SQLiteTypes type) { 
        Method method = null;
        try {
          method = preparedStatement.getClass().getMethod(
            type.getSetterString(),
            int.class, 
            type.getType());
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e) {
        }

        return method;
    }

    default String convertToJson(Object value) { 
        ObjectMapper objectMapper = new ObjectMapper();

        String json = null;
        try {
            json = objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}