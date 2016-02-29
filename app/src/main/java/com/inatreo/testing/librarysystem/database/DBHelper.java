package com.inatreo.testing.librarysystem.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vishal on 1/27/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "library_system.db";
    public static final int DATABASE_VERSION = 1;
    private Context context;

    private static final String TABLE_BOOKS = "books";
    private static final String BOOK_ID = "book_id";
    private static final String BOOK_NAME = "book_name";
    private static final String BOOK_QUANTITY = "book_quantity";
    private static final String BOOK_AVAILABLE_QUANTITY = "book_available_quantity";
    private static final String BOOK_CATEGORY = "book_category";
    private static final String BOOK_PRICE = "book_price";
    private static final String BOOK_AUTHOR = "book_author";
    private static final String BOOK_DESCRIPTION = "book_description";

    private static final String CREATE_TABLE_BOOK_QUERY = "create table "+ TABLE_BOOKS + "( " + BOOK_ID +
            " TEXT PRIMARY KEY, " + BOOK_NAME + " TEXT, " + BOOK_CATEGORY + " TEXT, " +
            BOOK_AUTHOR + " TEXT, " + BOOK_PRICE + " REAL, " +
            BOOK_QUANTITY + " INTEGER, " + BOOK_AVAILABLE_QUANTITY + " INTEGER, " +
            BOOK_DESCRIPTION + " TEXT)";

    private static final String CREATE_TABLE_ADMIN_QUERY = "create table admins( admin_id INTEGER PRIMARY KEY, "+
            " first_name TEXT, last_name TEXT, mobile_no TEXT, username TEXT, password TEXT, master_or_admin TEXT)";

    private static final String CREATE_TABLE_MEMBER_QUERY = "create table members( mobile_no TEXT PRIMARY KEY, "+
            "full_name TEXT, father_name TEXT, age INTEGER, level TEXT, address TEXT)";

    private static final String CREATE_TABLE_ISSUERECORD_QUERY = "create table issue_record( issue_id INTEGER PRIMARY KEY, "+
            "book_id TEXT, member_id TEXT, issue_date datetime default current_date)";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOOK_QUERY);
        db.execSQL(CREATE_TABLE_ADMIN_QUERY);
        db.execSQL(CREATE_TABLE_MEMBER_QUERY);
        db.execSQL(CREATE_TABLE_ISSUERECORD_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (db.needUpgrade(newVersion)){
            onCreate(db);
        }
    }
}
