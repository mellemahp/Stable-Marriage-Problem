package com.github.mellemahp.data_collection;

import lombok.NonNull;

public class SQLStatement {
    private String validatedStatement;

    public SQLStatement(@NonNull String statementUnvalidated) {
        if (isValidSQL(statementUnvalidated)) {
            validatedStatement = statementUnvalidated;
        }
    }

    private static boolean isValidSQL(@NonNull String statementUnvalidated) {
        return true;
    }

    public String getSQL() {
        return this.validatedStatement;
    }
}