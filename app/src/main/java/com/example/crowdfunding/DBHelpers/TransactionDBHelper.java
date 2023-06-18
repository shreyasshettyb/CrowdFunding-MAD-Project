package com.example.crowdfunding.DBHelpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.crowdfunding.Models.CollectorOverview;
import com.example.crowdfunding.Models.Transaction;

public class TransactionDBHelper extends SQLiteOpenHelper {

    public TransactionDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE transactions (donorName TEXT, upiID TEXT, transactionID TEXT PRIMARY KEY, dateTime DATETIME, amount NUMBER, collectorEmail TEXT, collectorName TEXT, fundingCode TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "DROP TABLE IF EXISTS transactions";
        db.execSQL(drop);
        onCreate(db);
    }

    public boolean addTransaction(Transaction t){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("donorName", t.getDonorName());
            values.put("upiID", t.getUpiID());
            values.put("transactionID", t.getTransactionID());
            values.put("dateTime", t.getDateTime());
            values.put("amount", t.getAmount());
            values.put("collectorEmail", t.getCollectorEmail());
            values.put("collectorName", t.getCollectorName());
            values.put("fundingCode", t.getFundingCode());
            long k = db.insert("transactions", null, values);
            db.close();
            return k != -1;
        }catch (Exception e){
            Log.e("myTag", e + "");
        }
        return false;
    }

    public Cursor getTransactions(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("Select * from transactions where email=?", new String[] {email});
        return cursor;
    }

    public CollectorOverview[] getCollectorOverview(String fundingCode){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            //Cursor cursor = db.rawQuery("Select * from transactions where fundingCode=?", new String[] {fundingCode});
//            if(cursor != null && cursor.moveToFirst())
//                return new CollectorOverview[]{new CollectorOverview("Error while fetching", 0.0)};
            db.close();
            return new CollectorOverview[] {new CollectorOverview("hello1", 10.0)} ;
        }catch (Exception e){
            Log.e("myTag", "" + e);
        }
        return new CollectorOverview[] {new CollectorOverview("Error while fetching", 0.0)} ;
    }
}
