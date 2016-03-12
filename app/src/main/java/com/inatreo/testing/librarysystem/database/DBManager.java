package com.inatreo.testing.librarysystem.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by vishal on 1/28/2016.
 */
public class DBManager {
    private static DBManager dbManager;
    private static DBHelper dbHelper;
    private Context context;

    protected DBManager(Context context){
        this.context = context;
    }

    public static DBManager getInstance(Context context){
        if (dbManager == null){
            dbManager = new DBManager(context);
        }
        return dbManager;
    }

    protected static SQLiteDatabase getDBInstance(Context context){
        if (dbHelper == null){
            dbHelper = new DBHelper(context);
        }
        return dbHelper.getWritableDatabase();
    }
}
