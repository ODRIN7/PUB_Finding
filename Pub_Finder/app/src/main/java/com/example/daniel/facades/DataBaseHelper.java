package com.example.daniel.facades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by DANIEL on 2016. 03. 24..
 */
public class  DataBaseHelper<T> extends OrmLiteSqliteOpenHelper {



    private Dao genericDao=null;
    private RuntimeExceptionDao<T,Integer> genericRuntimeDao=null;
    private Class<T> genericType;
    public static final String DATABASE_NAME = "PubFinder";
    public  static final int DATABASE_VERSION = 1;
    private Context context;

    public DataBaseHelper(Context context, Class<T> genericType) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.genericType=genericType;
    }



    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            if(!getGenericDao().isTableExists()) {
                TableUtils.createTable(connectionSource,genericType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            if(!getGenericDao().isTableExists()) {
                TableUtils.dropTable(connectionSource, genericType, true);
            }
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public <T> T createEntity(T entity) throws SQLException {
        if(!getGenericDao().isTableExists()) {
            onCreate(super.getWritableDatabase(), super.connectionSource);
        }
            getGenericDao().create(entity);
        return entity;
    }


    public <T> T updateEntity( T entity) throws SQLException {
        if(!getGenericDao().isTableExists()) {
            onCreate(super.getWritableDatabase(), super.connectionSource);
        }
        getGenericDao().createOrUpdate(entity);

        return entity;
    }


    public <T> T getEntityById(Class<T> entityClass, Integer id) throws SQLException {
        if(!getGenericDao().isTableExists()) {
            onCreate(super.getReadableDatabase(), super.connectionSource);
        }
        T getObject = (T) getGenericDao().queryForId((Object)id);
        return getObject;
    }


    public <T> T removeEntity(Class<T> entityClass, Integer id) throws SQLException {
        if(!getGenericDao().isTableExists()) {
            onCreate(super.getReadableDatabase(), super.connectionSource);
        }
        T entity = getEntityById(entityClass, id);
        getGenericDao().delete(entity);

        return entity;
    }
    public Dao getGenericDao() throws SQLException {
        if(genericDao==null){
            genericDao=getDao(genericType);
        }
        return genericDao;
    }

    public void setGenericDao(Dao genericDao) {
        this.genericDao = genericDao;
    }

}