package com.inatreo.testing.librarysystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inatreo.testing.librarysystem.models.Book;

import java.util.ArrayList;

/**
 * Created by vishal on 2/2/2016.
 */
public class CRUDBook{

    private Context context;
    private static CRUDBook crudBook;

    private static final String TABLE_BOOKS = "books";
    private static final String BOOK_ID = "book_id";
    private static final String BOOK_NAME = "book_name";
    private static final String BOOK_QUANTITY = "book_quantity";
    private static final String BOOK_AVAILABLE_QUANTITY = "book_available_quantity";
    private static final String BOOK_CATEGORY = "book_category";
    private static final String BOOK_PRICE = "book_price";
    private static final String BOOK_AUTHOR = "book_author";
    private static final String BOOK_DESCRIPTION = "book_description";

    private CRUDBook(Context context){
        this.context = context;
    }
    public static CRUDBook getInstance(Context context){
        if(crudBook == null)
            crudBook = new CRUDBook(context);
        return crudBook;
    }
    public void insert(final Book book){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = DBManager.getDBInstance(context);
                ContentValues cv = new ContentValues();
                cv.put(BOOK_ID, getBookId());
                cv.put(BOOK_NAME, book.getName());
                cv.put(BOOK_CATEGORY, book.getCategory());
                cv.put(BOOK_QUANTITY, book.getQuantity());
                cv.put(BOOK_AUTHOR, book.getAuthor());
                cv.put(BOOK_PRICE, book.getPrice());
                cv.put(BOOK_DESCRIPTION, book.getDescription());
                cv.put(BOOK_AVAILABLE_QUANTITY, 0);
                db.insert(TABLE_BOOKS, null, cv);
            }
        }).start();
    }

    //book id starts from 335001
    public int getBookId(){
        String query = "select * from " + TABLE_BOOKS;
        Cursor cursor = DBManager.getDBInstance(context).rawQuery(query, null);
        if (cursor.getCount() == 0){
            Log.v("-DBM-", "book id: " + String.valueOf(335001));
            cursor.close();
            return (335000 + 1);
        }
        else{
            Log.v("-DBM-","book id: " + String.valueOf(335001+cursor.getCount()));
            cursor.close();
            return (335000 + cursor.getCount() + 1);
        }
    }

    public Book getBookDetails(String bookID ){
        Book book = new Book();
        String query = "select * from " + TABLE_BOOKS + " where " + BOOK_ID + " = " + bookID;
        Cursor cursor = DBManager.getDBInstance(context).rawQuery(query, null);
        cursor.moveToFirst();
        Log.v("-DBM-", "checking..");
        while (!cursor.isAfterLast()){
            Log.v("-DBM-", cursor.getString(cursor.getColumnIndex(BOOK_NAME)));
            Log.v("-DBM-", cursor.getString(cursor.getColumnIndex(BOOK_CATEGORY)));
            /*---------------------------------------------------------------------*/
            book.setBookID(cursor.getString(cursor.getColumnIndex(BOOK_ID)));
            book.setName(cursor.getString(cursor.getColumnIndex(BOOK_NAME)));
            book.setCategory(cursor.getString(cursor.getColumnIndex(BOOK_CATEGORY)));
            book.setAuthor(cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)));
            book.setPrice(cursor.getInt(cursor.getColumnIndex(BOOK_PRICE)));
            book.setQuantity(cursor.getInt(cursor.getColumnIndex(BOOK_QUANTITY)));
            book.setDescription(cursor.getString(cursor.getColumnIndex(BOOK_DESCRIPTION)));
            cursor.moveToNext();
        }
        cursor.close();
        return book;
    }

    public ArrayList<Book> getAllBook(){
        ArrayList<Book> books = new ArrayList<>();

        String query = "select " + BOOK_ID + ", " + BOOK_AUTHOR + ", " + BOOK_NAME + " from " + TABLE_BOOKS ;
        Cursor cursor = DBManager.getDBInstance(context).rawQuery(query, null);
        cursor.moveToFirst();
        Log.v("-DBM-", "checking..");
        while (!cursor.isAfterLast()){
            Book book = new Book();
            Log.v("-DBM-", cursor.getString(cursor.getColumnIndex(BOOK_NAME)));
            /*---------------------------------------------------------------------*/
            book.setBookID(cursor.getString(cursor.getColumnIndex(BOOK_ID)));
            book.setName(cursor.getString(cursor.getColumnIndex(BOOK_NAME)));
            book.setAuthor(cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)));
            books.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        return books;
    }

    public boolean isBookPresent(String bookID) {
        String[] columns = {BOOK_ID};
        String selction = BOOK_ID + " = ?";
        String[] selectionArgs = {bookID};
        SQLiteDatabase db = DBManager.getDBInstance(context);
        Cursor cursor = db.query(TABLE_BOOKS, columns, selction, selectionArgs, null, null, null);
        if (cursor.getCount() == 1)
            return true;
        else return false;
    }
}
