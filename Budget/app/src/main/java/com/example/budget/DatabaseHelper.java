package com.example.budget;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "Budget.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Budget.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table users(email TEXT primary key, password TEXT)");
        MyDatabase.execSQL("create Table assetLiability(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, description TEXT, amount REAL, type TEXT)");
        MyDatabase.execSQL("create Table incomeExpense(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, description TEXT, amount REAL, tag TEXT, account TEXT, type TEXT)");
        MyDatabase.execSQL("create Table transfer(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, description TEXT, amount REAL, tag TEXT, src TEXT, dest TEXT)");
        MyDatabase.execSQL("create Table final(id INTEGER PRIMARY KEY , date TEXT, totalAsset REAL, totalLiability REAL, totalIncome REAL, totalExpense REAL)");
        insertDataToFinalTable(MyDatabase, 1,"NA", Float.parseFloat("0") ,Float.parseFloat("0") ,Float.parseFloat("0") ,Float.parseFloat("0") );
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        //MyDB.execSQL("drop Table if exists users");
        //MyDB.execSQL("drop Table if exists assetLiability");


    }

    public Boolean insertData(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            Log.d("BudgetDB", "Table: users email: " + email + " password: " + password);
            return true;
        }
    }

    public Boolean insertDataToAssetLiabilityTable(String date, String description, Float amount, String type){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("description", description);
        contentValues.put("amount", amount);
        contentValues.put("type", type);
        long result = MyDatabase.insert("assetLiability", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            Log.d("BudgetDB", "Table: assetLiability date: " + date + " description: " + description + " amount: " + amount + " type: " + type);
            return true;
        }
    }


    public Boolean insertDataToIncomeExpenseTable(String date, String description, Float amount, String tag, String account, String type){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("description", description);
        contentValues.put("amount", amount);
        contentValues.put("tag", tag);
        contentValues.put("account", account);
        contentValues.put("type", type);
        long result = MyDatabase.insert("incomeExpense", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            Log.d("BudgetDB", "Table: incomeExpense date: " + date + " description: " + description + " amount: " + amount + " tag: " + tag + " account: " + account+ " type: " + type);
            return true;
        }
    }

    public Boolean insertDataToTransferTable(String date, String description, Float amount, String tag, String src, String dest){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("description", description);
        contentValues.put("amount", amount);
        contentValues.put("tag", tag);
        contentValues.put("src", src);
        contentValues.put("dest", dest);
        long result = MyDatabase.insert("transfer", null, contentValues);


        if (result == -1) {
            return false;
        } else {
            Log.d("BudgetDB", "Table: transfer date: " + date + " description: " + description + " amount: " + amount + " tag: " + tag + " src: " + src + " dest: " + dest);
            return true;
        }
    }

    public Boolean insertDataToFinalTable(SQLiteDatabase MyDatabase, int iId, String date, Float totalAsset, Float totalLiability, Float totalIncome, Float totalExpense){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", iId);
        contentValues.put("date", date);
        contentValues.put("totalAsset", totalAsset);
        contentValues.put("totalLiability", totalLiability);
        contentValues.put("totalIncome", totalIncome);
        contentValues.put("totalExpense", totalExpense);

        long result = MyDatabase.insert("final", null, contentValues);


        if (result == -1) {
            return false;
        } else {
            Log.d("BudgetDB", "Table: final id: " + iId + "date: " + date + " totalAsset: " + totalAsset + " totalLiability: " + totalLiability + " totalIncome: " + totalIncome + " totalExpense: " + totalExpense);
            return true;
        }
    }

    public Boolean UpdateFinalTable(String date, Float totalAsset, Float totalLiability, Float totalIncome, Float totalExpense){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", 1);
        contentValues.put("date", date);
        contentValues.put("totalAsset", totalAsset);
        contentValues.put("totalLiability", totalLiability);
        contentValues.put("totalIncome", totalIncome);
        contentValues.put("totalExpense", totalExpense);
        String[] whereArgs = {String.valueOf(1)};
        String whereClause = "id = ?";
        long result = MyDatabase.update("final", contentValues, whereClause, whereArgs);

        if (result == -1) {
            return false;
        } else {
            Log.d("BudgetDB", "UpdateTable: final date: " + date + " totalAsset: " + totalAsset + " totalLiability: " + totalLiability + " totalIncome: " + totalIncome + " totalExpense: " + totalExpense);
            return true;
        }
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});

        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("Range")
    public Summary getSummary()
    {
        Summary summary = new Summary();
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        String[] projection = {"totalAsset" , "totalLiability" , "totalIncome" , "totalExpense" };
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(1)};
        Cursor cursor = MyDatabase.query("final", projection, selection, selectionArgs, null, null, null);
        //Cursor cursor = MyDatabase.rawQuery("Select * from final where email = 1", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            summary.fTotalAsset =  cursor.getFloat(cursor.getColumnIndex("totalAsset"));
            summary.fTotalLiability = cursor.getFloat(cursor.getColumnIndex("totalLiability"));
            summary.fTotalIncome = cursor.getFloat(cursor.getColumnIndex("totalIncome"));
            summary.fTotalExpense = cursor.getFloat(cursor.getColumnIndex("totalExpense"));
            return summary;
        }else {
            return null;
        }
    }

    public void queryAndUpdateFinalTable(float fAddAsset, float fAddLiability, float fAddIncome, float fAddExpense ){
        Summary summary = getSummary();
        summary.fTotalAsset += fAddAsset;
        summary.fTotalLiability += fAddLiability;
        summary.fTotalIncome += fAddIncome;
        summary.fTotalExpense += fAddExpense;
        UpdateFinalTable("NA", summary.fTotalAsset, summary.fTotalLiability, summary.fTotalIncome, summary.fTotalExpense);
    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
}