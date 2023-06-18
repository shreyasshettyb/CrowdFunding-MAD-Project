package com.example.crowdfunding;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.crowdfunding.Models.User;


public class UserDBHelper extends SQLiteOpenHelper {

    public UserDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, password TEXT, type TEXT, fundingCode TEXT, fundingName TEXT)";
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
            values.put("fundingName", user.getFundingName());
            long k = db.insert("users", null, values);
            db.close();
            return k != -1;
        }
        catch(Exception e){
            Log.e("myTag", "" + e);
        }
        return false;
    }

    public boolean checkUser(String email, String password){
        SQLiteDatabase db;
        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.query("users", new String[]{"email"}, "email=? and password=?", new String[]{email, password}, null, null, null);
            boolean t = cursor != null && cursor.moveToFirst();
            if(cursor != null)
                cursor.close();
            db.close();
            return t;
        }
        catch(Exception e){
            Log.e("myTag", "" + e);
        }
        return false;
    }

    public String getUserName(String email){
        SQLiteDatabase db;
        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.query("users", new String[] {"name"}, "email=?", new String[]{email}, null, null, null);
            if(! cursor.moveToFirst())
                return "";
            String name = cursor.getString(0);
            cursor.close();
            db.close();
            return name;
        }
        catch(Exception e){
            Log.e("myTag", "" + e);
        }
        return "";
    }

    public boolean isFundingCodePresent(String fundingCode){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query("users", new String[]{"email"}, "fundingCode=?", new String[]{fundingCode}, null, null, null);
            boolean ret = cursor != null && cursor.moveToFirst();
            if(cursor != null)
                cursor.close();
            db.close();
            return ret;
        }catch (Exception e){
            Log.e("myTag", "" + e);
        }
        return false;
    }
    public Bundle getInfo(String email){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query("users", new String[]{"type", "fundingCode", "fundingName"}, "email=?", new String[]{email}, null, null, null);

            if(!cursor.moveToFirst())
                return null;
            Bundle bundle = new Bundle();
            bundle.putString("FundingName", cursor.getString(2));
            bundle.putString("FundingCode", cursor.getString(1));
            if (cursor.getString(0).equals("Admin")) {
                bundle.putString("Text", "Want to add volunteers? Share this code\n                        " + cursor.getString(1));
            }
            else
                bundle.putString("Text", "Thanks for being a volunteer");
            cursor.close();
            db.close();
            return bundle;
        }catch(Exception e){
            Log.e("myTag", "" + e);
        }
        return null;
    }

}
