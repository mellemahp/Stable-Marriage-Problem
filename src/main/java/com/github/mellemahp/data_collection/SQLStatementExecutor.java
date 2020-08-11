package com.github.mellemahp.data_collection;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lombok.CustomLog;

import java.sql.SQLException;

@CustomLog
public class SQLStatementExecutor {
    private final List<SQLStatement> sqlActions;

    public SQLStatementExecutor() {
        sqlActions = new ArrayList<>();
    }

    public void add(SQLStatement sqlStatement) {
        sqlActions.add(sqlStatement);
    }

    public void clear() {
        sqlActions.clear();
    }

    public void execute(Statement connectionStatement) {
        for (SQLStatement action : sqlActions) {
            String actionString = action.getSQL();
            try {
                connectionStatement.executeUpdate(actionString);
            } catch (SQLException e) {
                log.info(e.getMessage());
            }
        }
    }
}