package facade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;


/**
 * Created by DANIEL on 2016. 03. 23..
 */
public class DatabaseHandler {

    public static final String DATABASE_NAME = "PubFinder";
    public  static final int DATABASE_VERSION = 1;
    private Context context;
    private DataBaseHelper dBHelper;

    public DatabaseHandler(Context context) {
        this.context=context;
        dBHelper = new DataBaseHelper(context,DATABASE_NAME,DATABASE_VERSION);
    }


    public <T> T createEntity(Class<T> entityClass, T entity ) {
        SQLiteDatabase db = dBHelper.getWritableDatabase();
        ContentValues cvEntity =new ContentValues();
        int num=0;

        for (Field fieldattribute : entityClass.getFields() ){
            cvEntity.put(fieldattribute.getName(),fieldattribute.toGenericString());
        }
        db.insert(entityClass.getName(),null,cvEntity);
        return entity;
    }
    public <T> T updateEntity(Class<T> entityClass, T entity) {
        SQLiteDatabase db = dBHelper.getWritableDatabase();
        ContentValues cvEntity =new ContentValues();
        int num=0;
        for (Field fieldattribute : entityClass.getFields() ){
            cvEntity.put(fieldattribute.getName(),fieldattribute.toGenericString());
        }
        db.insert(entityClass.getName(),null,cvEntity);
        return entity;
    }
    public void removeEntity(Class entityClass, Long id) {
        SQLiteDatabase db = dBHelper.getWritableDatabase();
        db.delete(entityClass.getName(),getEntityById(entityClass,id).toString(),null);
    }
    public <T> T getEntityById(Class<T> entityClass, Long id) {
        SQLiteDatabase db = dBHelper.getReadableDatabase();
       // String from[] = { "*"};
        String where = id+"="+entityClass.getFields()[0].getName();
        Cursor cursor = db.query(true, entityClass.getName(), null, where, null, null, null, null, null);
        db.close();
       // return (entityClass) cursor ;
        return  null;
    }

}
