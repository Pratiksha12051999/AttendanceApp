package com.example.attendanceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "registerUser";
    public static final String TABLE_NAME_2 = "teachers";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EmailID";
    public static final String COL_3 = "AdmissionNo";
    public static final String COL_4 = "Password";
    public static final String COL_5 = "email";
    public static final String COL_6 = "password";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE registerUser (ID INTEGER PRIMARY KEY AUTOINCREMENT, EmailID TEXT, AdmissionNo TEXT, Password TEXT)");
        db.execSQL("CREATE TABLE teachers (email TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }

    public long addUser(String email, String admissionno, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EmailID", email);
        contentValues.put("AdmissionNo", admissionno);
        contentValues.put("Password", password);
        long res = db.insert("registerUser", null, contentValues);
        db.close();
        return res;
    }

    public long addTeacher(String email, String password){
        SQLiteDatabase db2 = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("email", email);
        contentValues1.put("password", password);
        long res2 = db2.insert("teachers", null, contentValues1);
        db2.close();
        return res2;
    }

    public boolean checkUser(String email, String admissionno, String password){
        String[] columns = {COL_2};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?" + " and " + COL_4 + "=?";
        String[] selectionArgs = {email, admissionno, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkTeacher(String teacheremail, String teacherpassword){
        String[] columns = { COL_5 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_5 + "=?" + " and " + COL_6 + "=?";
        String[] selectionArgs = {teacheremail, teacherpassword};
        Cursor cursor1 = db.query(TABLE_NAME_2, columns, selection, selectionArgs, null, null,null);
        Log.i("Teachers",cursor1.toString());
        int count1 = cursor1.getCount();
        cursor1.close();
        db.close();
        if(count1 > 0){
            return true;
        }
        else{
            return false;
        }
    }
}
