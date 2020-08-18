package com.github.mellemahp.sqlite_data_processing;

import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.PreparedStatement;


public enum SQLiteType {
    INTEGER("integer", "setInt", int.class),
    REAL("real", "setDouble", double.class),
    TEXT("text", "setString", String.class),
    BLOB("blob", "setBlob", Blob.class);

    private String sqlTypeStr;
    private String method;
    private Class<?> typeCls;
 
    SQLiteType(String sqlTypeStr, String method, Class<?> typeCls) {
        this.sqlTypeStr = sqlTypeStr;
        this.method = method;
        this.typeCls = typeCls;
    }

    @Override
    public String toString() { 
        return this.sqlTypeStr;
    }

    public Class<?> getTypeCls() { 
        return this.typeCls;
    }

    public Method getMethod() {
        Class<?>[] paramTypes = { int.class, this.typeCls };
        Method preparedStatementMethod = null;
        try { 
            preparedStatementMethod = PreparedStatement.class.getDeclaredMethod(
                this.method, paramTypes
            );
        } catch (NoSuchMethodException e) { 
            e.printStackTrace();
        }
        return preparedStatementMethod;
    }
}