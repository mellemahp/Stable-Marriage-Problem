package com.github.mellemahp.data_collection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.reflect.FieldUtils;

public interface SQLiteTableDefinition {
    public String getTableDefinition();

    default String generateTableDefinition(Class<? extends SQLiteSerializable> cls) {
        return String.format("CREATE TABLE IF NOT EXISTS %s(%s);",
                getTableName(cls),
                getSQLColumnDefinitions(cls));
    }

    default String getTableName(Class<? extends SQLiteSerializable> cls) {
        return cls.getSimpleName()
                .replace("DataContainer", "")
                .toLowerCase();
    }

    default String getSQLColumnDefinitions(Class<? extends SQLiteSerializable> cls) {
        List<Field> fields = FieldUtils.getFieldsListWithAnnotation(cls, SQLiteField.class);

        return fields.stream()
                .map(this::getColumnString)
                .collect(Collectors.joining(","));
    }

    default String getColumnString(Field field) {
        List<String> columnStringList = new ArrayList<>();
        SQLiteField annotation = field.getAnnotation(SQLiteField.class);

        columnStringList.add(field.getName());
        columnStringList.add(annotation.type().toString());
        columnStringList.add(getColumnClassifierString(field, annotation));

        return columnStringList.stream().collect(Collectors.joining(" "));
    }

    default String getColumnClassifierString(Field field, SQLiteField annotation) {
        String columnClassifierString = "";

        if (field.isAnnotationPresent(PrimarySQLKey.class)) {
            columnClassifierString = "PRIMARY KEY";
        } else if (annotation.nonNull()) {
            columnClassifierString = "NOT NULL";
        }

        return columnClassifierString;
    }
}