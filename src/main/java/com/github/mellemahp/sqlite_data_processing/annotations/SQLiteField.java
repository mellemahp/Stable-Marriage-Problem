package com.github.mellemahp.sqlite_data_processing.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.mellemahp.sqlite_data_processing.SQLiteType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SQLiteField {

    boolean nonNull() default false;

    SQLiteType type();

    boolean json() default false;
    
}