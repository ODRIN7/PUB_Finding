package facade;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;

import entities.User;

/**
 * Created by DANIEL on 2016. 03. 24..
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context,String DATABASE_NAME,int DATABASE_VERSION) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        String CREATE_CONTACTS_TABLE = "CREATE TABLE USER( " +
                "user_id Integer Primary KEY AUTOINCREMENT,"+
                "name VARCHAR(255), password VARCHAR(255)"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

}