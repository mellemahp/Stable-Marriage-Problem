package com.github.mellemahp.sqlite_data_processing;

import java.sql.Connection;
import java.sql.SQLException;

public interface SQLiteExecutable {
    public void execute(Connection connection) throws SQLException;
}