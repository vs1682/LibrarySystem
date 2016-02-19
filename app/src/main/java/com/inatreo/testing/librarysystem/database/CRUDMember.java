package com.inatreo.testing.librarysystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inatreo.testing.librarysystem.models.Member;

import java.util.ArrayList;

/**
 * Created by vishal on 2/3/2016.
 */
public class CRUDMember {

    private Context context;
    private static CRUDMember crudMember;

    private static final String TABLE_MEMBER = "members";
    private static final String MEMBER_ID = "member_id";
    private static final String MEMBER_FULLNAME = "full_name";
    private static final String MEMBER_FATHER_NAME = "father_name";
    private static final String MEMBER_AGE = "age";
    private static final String MEMBER_LEVEL = "level";
    private static final String MEMBER_MOBILE = "mobile_no";
    private static final String MEMBER_ADDRESS = "address";

    private CRUDMember(Context context){
        this.context = context;
    }

    public static CRUDMember getInstance(Context context){
        if (crudMember == null)
            crudMember = new CRUDMember(context);
        return crudMember;
    }

    public void insertMember(final Member member){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = DBManager.getDBInstance(context);
                ContentValues cv = new ContentValues();
                cv.put(MEMBER_ID, member.getMemberID());
                cv.put(MEMBER_FULLNAME, member.getFullName());
                cv.put(MEMBER_FATHER_NAME, member.getFatherName());
                cv.put(MEMBER_AGE, member.getAge());
                cv.put(MEMBER_LEVEL, member.getLevel());
                cv.put(MEMBER_MOBILE, member.getMobileNo());
                cv.put(MEMBER_ADDRESS, member.getAddress());
                db.insert(TABLE_MEMBER, null, cv);
            }
        }).start();
    }

    public Member getMemberDetails(int memberID){
        Member member = new Member();
        String query = "select * from " + TABLE_MEMBER + " where " + MEMBER_ID + " = " + memberID;
        SQLiteDatabase db = DBManager.getDBInstance(context);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            member.setMemberID(cursor.getString(cursor.getColumnIndex(MEMBER_ID)));
            member.setFullName(cursor.getString(cursor.getColumnIndex(MEMBER_FULLNAME)));
            member.setFatherName(cursor.getString(cursor.getColumnIndex(MEMBER_FATHER_NAME)));
            member.setAge((cursor.getInt(cursor.getColumnIndex(MEMBER_AGE))));
            member.setLevel(cursor.getString(cursor.getColumnIndex(MEMBER_LEVEL)));
            member.setMobileNo(cursor.getString(cursor.getColumnIndex(MEMBER_MOBILE)));
            member.setAddress(cursor.getString(cursor.getColumnIndex(MEMBER_ADDRESS)));

            Log.v("-CRUDMember-", cursor.getString(cursor.getColumnIndex(MEMBER_FULLNAME)));
            cursor.moveToNext();
        }
        cursor.close();
        return member;
    }

    public ArrayList<Member> getAllMember(){
        ArrayList<Member> members = new ArrayList<>();
        String query = "select * from " + TABLE_MEMBER ;
        SQLiteDatabase db = DBManager.getDBInstance(context);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Member member = new Member();
            member.setMemberID(cursor.getString(cursor.getColumnIndex(MEMBER_ID)));
            member.setFullName(cursor.getString(cursor.getColumnIndex(MEMBER_FULLNAME)));
            member.setFatherName(cursor.getString(cursor.getColumnIndex(MEMBER_FATHER_NAME)));
            member.setAge(cursor.getInt(cursor.getColumnIndex(MEMBER_AGE)));
            members.add(member);
            Log.v("-CRUDMember-", cursor.getString(cursor.getColumnIndex(MEMBER_FULLNAME)));
            cursor.moveToNext();
        }
        cursor.close();

        return members;
    }
}
