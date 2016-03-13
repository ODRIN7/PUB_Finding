package com.example.daniel.pub_finder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DANIEL on 2016. 03. 13..
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PubFinding_DB";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public <T> T createEntity(Class<T> entityClass, T entity, SQLiteDatabase db) {
        return entity;
    }
    public <T> T updateEntity(Class<T> entityClass, T entity) {
      return    entity;
    }
    public void removeEntity(Class entityClass, Long id) {

    }
    public <T> T getEntityById(Class<T> entityClass, Long id) {
        return null;
    }



}
