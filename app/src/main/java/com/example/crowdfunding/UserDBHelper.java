package com.example.crowdfunding;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.crowdfunding.User;

public class UserDBHelper extends SQLiteOpenHelper {

    public UserDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE users (id NUMBER PRIMARY KEY, name TEXT, email TEXT, password TEXT, type TEXT, fundingCode TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "DROP TABLE IF EXISTS users";
        db.execSQL(drop);
        onCreate(db);
    }

    public boolean addUser(User user){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", user.getName());
            values.put("email", user.getEmail());
            values.put("password", user.getPassword());
            values.put("type", user.getType());
            values.put("fundingCode", user.getFundingCode());
            long k = db.insert("users", null, values);
            db.close();
            return k != -1;
        }
        catch(Exception e){
            Log.e("mytag", "" + e);
        }
        return false;
    }

    public boolean checkUser(String email, String password){
        SQLiteDatabase db;
        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.query("users", new String[]{"email"}, "email=? and password=?", new String[]{email, password}, null, null, null);
            boolean t = cursor != null && cursor.moveToFirst();
            db.close();
            return t;
        }
        catch(Exception e){
            Log.e("mytag", "" + e);
        }
        return false;
    }

    public boolean isUserPresent(String email){
        SQLiteDatabase db;
        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.query("users", new String[] {"email"}, "email=?", new String[]{email}, null, null, null);
            boolean t = cursor != null && cursor.moveToFirst();
            db.close();
            return t;
        }
        catch(Exception e){
            Log.e("mytag", "" + e);
        }
        return false;
    }

    public boolean isFundingCodePresent(String fundingCode){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            @SuppressLint("Recycle")
            Cursor cursor = db.query("users", new String[]{"email"}, "fundingCode=?", new String[]{fundingCode}, null, null, null);
            boolean ret = cursor != null && cursor.moveToFirst();
            db.close();
            return ret;
        }catch (Exception e){
            Log.e("mytag", "" + e);
        }
        return false;
    }
    public String getText(String email){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query("users", new String[]{"type", "fundingCode"}, "email=?", new String[]{email}, null, null, null);
            cursor.moveToFirst();
            String text = "Thanks for being a volunteer";
            if (cursor != null && cursor.moveToFirst() && cursor.getString(0).equals("Admin"))
                text = "Want to add volunteers? Share this code\n                        " + cursor.getString(1);
            db.close();
            return text;
        }catch(Exception e){
            Log.e("mytag", "" + e);
        }
        return "(Unable to generate the Funding Code)";
    }
}
