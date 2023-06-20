package com.example.crowdfunding;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.crowdfunding.Models.CollectorOverview;
import com.example.crowdfunding.Models.DonorOverview;
import com.example.crowdfunding.Models.Transaction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class TransactionDBHelper extends SQLiteOpenHelper {

    public TransactionDBHelper(@Nullable Context context, @Nullable String name, @Nullable
    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE transactions (transactionID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "donorName TEXT, upiID TEXT, dateTime TEXT, amount NUMBER, collectorEmail TEXT, " +
                "collectorName TEXT, fundingCode TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "DROP TABLE IF EXISTS transactions";
        db.execSQL(drop);
        onCreate(db);
    }

    public boolean addTransaction(Transaction t) {
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
        } catch (Exception e) {
            Log.e("myTag", e + "");
        }
        return false;
    }


    public CollectorOverview[] getCollectorOverview(String fundingCode) {
        try {
            CollectorOverview[] transactionList;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query("transactions",
                    new String[]{"collectorName, collectorEmail", "SUM(amount) AS totalAmount"},
                    "fundingCode=?",
                    new String[]{fundingCode}, "collectorEmail", null, null);
            if (!cursor.moveToFirst())
                return new CollectorOverview[]{};
            int count = cursor.getCount();
            transactionList = new CollectorOverview[count];
            for (int i = 0; i < count; i++) {
                transactionList[i] = new CollectorOverview(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getDouble(2));
                cursor.moveToNext();
            }

            cursor.close();
            db.close();
            return transactionList;
        } catch (Exception e) {
            Log.e("myTag", "" + e);
        }
        return new CollectorOverview[]{new CollectorOverview("Error", "Try Again",
                0.0)};
    }

    public DonorOverview[] getCollectorSpecific(String collectorEmail, int dateT, int amtASC, int amtDESC) {
        String sortBy;
        try {
            if (dateT == 1)
                sortBy = "dateTime";
            else if (amtASC == 1)
                sortBy = "amount ASC";
            else
                sortBy = "amount DESC";

            DonorOverview[] transactionList;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query("transactions", new String[]{"donorName, upiID, dateTime, amount"},
                    "collectorEmail=?", new String[]{collectorEmail}, null, null, sortBy);

            if (!cursor.moveToFirst())
                return new DonorOverview[]{};
            int count = cursor.getCount();
            transactionList = new DonorOverview[count];
            for (int i = 0; i < count; i++) {
                transactionList[i] = new DonorOverview(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2), cursor.getDouble(3));
                cursor.moveToNext();
            }

            cursor.close();
            db.close();
            return transactionList;
        } catch (Exception e) {
            Log.e("myTag", "" + e);
        }
        return new DonorOverview[]{new DonorOverview("Error", "Try Again",
                "", 0.0)};
    }

    public boolean exportTransactionsToCSV(String fundingC) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM transactions where fundingCode=?";

        Cursor cursor = db.rawQuery(query, new String[]{fundingC});

        File csvFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "transactions.csv");

        try {
            FileWriter writer = new FileWriter(csvFile);

            writer.append("TransactionID,DonorName,TypeOfPayment,UpiID,DateTime,Amount,CollectorEmail,CollectorName\n");

            if (cursor.moveToFirst()) {
                do {
                    int transactionID = cursor.getInt(0);
                    String donorName = cursor.getString(1);
                    String upiID = cursor.getString(2);
                    String typeOfPayment = "UPIPayment";
                    if (upiID.equals(""))
                        typeOfPayment = "CASH";
                    String dateTime = cursor.getString(3);
                    double amount = cursor.getDouble(4);
                    String collectorEmail = cursor.getString(5);
                    String collectorName = cursor.getString(6);

                    writer.append(String.valueOf(transactionID)).append(",");
                    writer.append(donorName).append(",");
                    writer.append(typeOfPayment).append(",");
                    writer.append(upiID).append(",");
                    writer.append(dateTime).append(",");
                    writer.append(String.valueOf(amount)).append(",");
                    writer.append(collectorEmail).append(",");
                    writer.append(collectorName).append("\n");
                } while (cursor.moveToNext());
            }

            cursor.close();
            writer.flush();
            writer.close();

            Log.d("myTag", "CSV exported successfully. Path: " + csvFile.getAbsolutePath());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("myTag", "Failed to export CSV: " + e.getMessage());
            return false;
        }
    }

}
