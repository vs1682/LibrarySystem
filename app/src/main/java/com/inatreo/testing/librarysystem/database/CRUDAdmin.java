package com.inatreo.testing.librarysystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inatreo.testing.librarysystem.models.Admin;

/**
 * Created by vishal on 2/3/2016.
 */
public class CRUDAdmin {
    private Context context;
    private static CRUDAdmin crudAdmin;

    private final String TABLE_ADMIN = "admins";
    private final String ADMIN_ID = "admin_id";
    private final String ADMIN_FIRSTNAME = "first_name";
    private final String ADMIN_LASTNAME = "last_name";
    private final String ADMIN_MOBILE = "mobile_no";
    private final String ADMIN_USERNAME = "username";
    private final String ADMIN_PASSWORD = "password";
    private final String ADMIN_MASTER_OR_ADMIN = "master_or_admin";

    private CRUDAdmin(Context context){
        this.context = context;
    }

    public static CRUDAdmin getInstance(Context context){
        if (crudAdmin == null)
            crudAdmin = new CRUDAdmin(context);
        return crudAdmin;
    }

    public void insertAdmin(final Admin admin){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = DBManager.getDBInstance(context);
                ContentValues cv = new ContentValues();
                cv.put(ADMIN_FIRSTNAME, admin.getFirstName());
                cv.put(ADMIN_LASTNAME, admin.getLastName());
                cv.put(ADMIN_MOBILE, admin.getMobile());
                cv.put(ADMIN_USERNAME, admin.getUsername());
                cv.put(ADMIN_PASSWORD, admin.getPassword());
                cv.put(ADMIN_MASTER_OR_ADMIN, admin.getMasterOrAdmin());
                db.insert(TABLE_ADMIN, null, cv);
            }
        }).start();
    }

    public void getAllAdminDetails(){
        SQLiteDatabase db = DBManager.getDBInstance(context);
        String query = "select * from " + TABLE_ADMIN;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Log.v("-CRUDAdmin-", cursor.getString(cursor.getColumnIndex(ADMIN_FIRSTNAME)));
            Log.v("-CRUDAdmin-", cursor.getString(cursor.getColumnIndex(ADMIN_ID)));
            Log.v("-CRUDAdmin-", cursor.getString(cursor.getColumnIndex(ADMIN_MASTER_OR_ADMIN)));
            Log.v("-CRUDAdmin-", cursor.getString(cursor.getColumnIndex(ADMIN_USERNAME)));
            cursor.moveToNext();
        }
        cursor.close();
    }

    public boolean verifyAdmin(String username, String password){
        SQLiteDatabase db = DBManager.getDBInstance(context);
        /*String query = "select " + ADMIN_PASSWORD + " from " + TABLE_ADMIN + " where " +
                ADMIN_USERNAME + "=" + username;*/
        String[] columns = {ADMIN_PASSWORD};
        String selection = ADMIN_USERNAME+" = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_ADMIN, columns, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();
        String passwordFromDb = cursor.getString(cursor.getColumnIndex(ADMIN_PASSWORD));
        if (passwordFromDb.equals(password)){
                Log.v("-CRUDAdmin-", cursor.getString(cursor.getColumnIndex(ADMIN_PASSWORD)));
                Log.v("-CRUDAdmin-", password);
                Log.v("-CRUDAdmin-", "matched");
                cursor.close();
                return true;
            }
            else{
                Log.v("-CRUDAdmin-", cursor.getString(cursor.getColumnIndex(ADMIN_PASSWORD)));
                Log.v("-CRUDAdmin-", password);
                Log.v("-CRUDAdmin-", "didn't match");
                cursor.close();
                return false;
            }
    }

    public void deleteAdmin(String username){
        String whereClause = ADMIN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        SQLiteDatabase db = DBManager.getDBInstance(context);
        db.delete(TABLE_ADMIN, whereClause, selectionArgs);
    }
}
