package com.example.crowdfunding;

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
        String create = "CREATE TABLE transactions (transactionID INTEGER PRIMARY KEY AUTOINCREMENT, donorName TEXT, upiID TEXT, dateTime TEXT, amount NUMBER, collectorEmail TEXT, collectorName TEXT, fundingCode TEXT)";
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


    public CollectorOverview[] getCollectorOverview(String fundingCode){
        try{
            CollectorOverview[] transactionList;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query("transactions", new String[] {"collectorName, collectorEmail", "SUM(amount) AS totalAmount"}, "fundingCode=?", new String[]{fundingCode}, "collectorEmail", null, null);
            if(! cursor.moveToFirst())
                return new CollectorOverview[]{};
            int count = cursor.getCount();
            transactionList = new CollectorOverview[count];
            for(int i = 0; i < count; i++) {
                transactionList[i] = new CollectorOverview(cursor.getString(0), cursor.getString(1), cursor.getDouble(2));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
            Log.d("myTag", transactionList[0].getCollectorName() + " " + transactionList[1].getCollectorName());
            return transactionList;
        }catch (Exception e){
            Log.e("myTag", "" + e);
        }
        return new CollectorOverview[] {new CollectorOverview("Error", "Try Again", 0.0)} ;
    }

}
