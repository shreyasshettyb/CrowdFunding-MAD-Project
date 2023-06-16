package com.example.crowdfunding;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.crowdfunding.Transaction;

public class TransactionDBHelper extends SQLiteOpenHelper {

    public TransactionDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE transactions (donorName TEXT, upiID TEXT, transactionID TEXT PRIMARY KEY, dateTime TEXT, amount NUMBER, volunteeremail TEXT";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "DROP TABLE IF EXISTS transactions";
        db.execSQL(drop);
        onCreate(db);
    }

    public boolean addTransaction(Transaction t){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("donorName", t.getDonorName());
        values.put("upiID", t.getUpiID());
        values.put("transactionID", t.getTransactionID());
        values.put("dateTime", t.getDateTime());
        values.put("amount", t.getAmount());
        values.put("volunteeremail", t.getVolunteeremail());
        long k = db.insert("users", null, values);
        db.close();
        return k != -1;
    }

    public Cursor getTransactions(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("Select * from transactions where email=?", new String[] {email});
        return cursor;
    }
}
