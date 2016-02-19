package com.inatreo.testing.librarysystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inatreo.testing.librarysystem.models.IssueRecord;

import java.util.ArrayList;

/**
 * Created by vishal on 2/5/2016.
 */
public class CRUDIssueRecord {
    private Context context;
    private static CRUDIssueRecord crudIssueRecord;

    private static final String TABLE_ISSUERECORD = "issue_record";
    private static final String ISSUE_ID = "issue_id";
    private static final String BOOK_ID = "book_id";
    private static final String MEMBER_ID = "member_id";

    private CRUDIssueRecord(Context context){
        this.context = context;
    }

    public static CRUDIssueRecord getInstance(Context context){
        if (crudIssueRecord == null)
            crudIssueRecord = new CRUDIssueRecord(context);
        return crudIssueRecord;
    }

    public void insertIssueRecord(final IssueRecord issueRecord){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = DBManager.getDBInstance(context);
                ContentValues cv = new ContentValues();
                cv.put(BOOK_ID, issueRecord.getBookID());
                cv.put(MEMBER_ID, issueRecord.getMemberID());
                long count = db.insert(TABLE_ISSUERECORD, null, cv);
                Log.v("-CRUDIssue-", String.valueOf(count));
            }
        }).start();
    }

    public ArrayList<String> getIssueDetails(String bookID){
        ArrayList<String> memberIDs = new ArrayList<>();
        String[] columns = {MEMBER_ID};
        String selection = BOOK_ID + "=?";
        String[] selectionArgs = {bookID};
        Cursor cursor = DBManager.getDBInstance(context).query(TABLE_ISSUERECORD, columns, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            memberIDs.add(cursor.getString(cursor.getColumnIndex(MEMBER_ID)));
            cursor.moveToNext();
        }
        cursor.close();
        return memberIDs;
    }

    public void readAllRecords(){
        String query = "select * from " + TABLE_ISSUERECORD;
        Cursor cursor = DBManager.getDBInstance(context).rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Log.v("-CRUDIssue-", cursor.getString(cursor.getColumnIndex(BOOK_ID)));
            Log.v("-CRUDIssue-", cursor.getString(cursor.getColumnIndex(MEMBER_ID)));
            Log.v("-CRUDIssue-", cursor.getString(cursor.getColumnIndex("issue_date")));
            cursor.moveToNext();
        }
        cursor.close();
    }

}
