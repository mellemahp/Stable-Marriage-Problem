package com.github.mellemahp.data_collection;

import java.sql.Blob;

public enum SQLiteTypes {
    INTEGER("integer", "setInt", Integer.class),
    REAL("real", "setDouble", Double.class),
    TEXT("text", "setString", String.class),
    BLOB("blob", "setBlob", Blob.class);

    private String type;
    private String setter;
    private Class<?> cls;
 
    SQLiteTypes(String type, String setter, Class<?> cls) {
        this.type = type;
        this.setter = setter;
        this.cls = cls;
    }

    @Override
    public String toString() { 
        return this.type;
    }

    public String getSetterString() { 
        return this.setter;
    }

    public Class<?> getType() { 
        return this.cls;
    }
}