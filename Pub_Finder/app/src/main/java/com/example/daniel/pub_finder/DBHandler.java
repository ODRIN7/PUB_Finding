package com.example.daniel.pub_finder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DANIEL on 2016. 03. 23..
 */
public class DBHandler {

    public static final String DATABASE_NAME = "PubFinder";
    public  static final int DATABASE_VERSION = 1;
    private Context context;
    private DatabaseHandler dBHelper;

    public DBHandler(Context context) {
        this.context=context;
        dBHelper = new DatabaseHandler(context);
    }

    public  long addUser(String name){
        SQLiteDatabase db = dBHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        long id = db.insert("users",null,cv);
        db.close();
        return id;
    }
    public Cursor loadUsers(){
        SQLiteDatabase db = dBHelper.getReadableDatabase();
        Cursor cursor = db.query("users", null, null, null, null, null, null);
        cursor.moveToFirst();
        db.close();
        return  cursor;
    }


    public class DatabaseHandler extends SQLiteOpenHelper {

        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE users( " +
                    "_id Integer Primary KEY AUTOINCREMENT,"+
                    "name VARCHAR(255))";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
        }

    }
}
