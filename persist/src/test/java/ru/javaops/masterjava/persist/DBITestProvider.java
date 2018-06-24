package ru.javaops.masterjava.persist;

import java.sql.DriverManager;

public class DBITestProvider {
    public static void initDBI() {
        DBIProvider.initDBI("jdbc:postgresql://localhost:5432/masterjava", "user", "password");
    }
}