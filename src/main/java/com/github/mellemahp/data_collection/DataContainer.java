package com.github.mellemahp.data_collection;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.lang3.reflect.FieldUtils;


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

    default String getValueFillerString() {
        List<Field> fields = getAnnotatedFields();
        int numFillers = fields.size();
        List<String> fillerStrings = new ArrayList<>();
        for (int i = 0; i < numFillers; i++) {
            fillerStrings.add("?");
        }
        return String.join(",", fillerStrings);
    }

    public default String getTableName() {
        return this.getClass()
            .getSimpleName()
            .split("Container")[0]
            .toLowerCase();
    }
}