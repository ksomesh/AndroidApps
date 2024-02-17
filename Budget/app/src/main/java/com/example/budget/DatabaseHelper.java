package com.example.budget;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public void resetFinDB()
    {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        MyDatabase.execSQL("drop Table if exists assetLiability");
        MyDatabase.execSQL("drop Table if exists incomeExpense");
        MyDatabase.execSQL("drop Table if exists transfer");
        MyDatabase.execSQL("drop Table if exists final");
        MyDatabase.execSQL("create Table assetLiability(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, description TEXT, amount REAL, type TEXT)");
        MyDatabase.execSQL("create Table incomeExpense(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, description TEXT, amount REAL, tag TEXT, account TEXT, type TEXT)");
        MyDatabase.execSQL("create Table transfer(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, description TEXT, amount REAL, tag TEXT, src TEXT, dest TEXT)");
        MyDatabase.execSQL("create Table final(id INTEGER PRIMARY KEY , date TEXT, totalAsset REAL, totalLiability REAL, totalIncome REAL, totalExpense REAL)");
        insertDataToFinalTable(MyDatabase, 1,"NA", Float.parseFloat("0") ,Float.parseFloat("0") ,Float.parseFloat("0") ,Float.parseFloat("0") );
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

        return cursor.getCount() > 0;
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

        return cursor.getCount() > 0;
    }

    public double getBalanceForAcc(String strAcc)
    {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        String[] projection = {"amount" , "type"};
        String selection = "account = ?";
        String[] selectionArgs = {strAcc};
        Cursor cursor = MyDatabase.query("incomeExpense", projection, selection, selectionArgs, null, null, null);
        double dTotBalanceForAcc = 0;

        while (cursor.moveToNext())  {
            @SuppressLint("Range") String strType = cursor.getString(cursor.getColumnIndex("type"));
            @SuppressLint("Range") double dAmount = cursor.getFloat(cursor.getColumnIndex("amount"));
            if(strType.equals("income")) {
                dTotBalanceForAcc += dAmount;
            }
            else {
                dTotBalanceForAcc -= dAmount;
            }
        }


        projection = new String[]{"amount"};
        selection = "dest = ?";
        selectionArgs = new String[]{strAcc};
        cursor = MyDatabase.query("transfer", projection, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") double dAmount = cursor.getFloat(cursor.getColumnIndex("amount"));
            dTotBalanceForAcc += dAmount;
        }

        projection = new String[]{"amount"};
        selection = "src = ?";
        selectionArgs = new String[]{strAcc};
        cursor = MyDatabase.query("transfer", projection, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") double dAmount = cursor.getFloat(cursor.getColumnIndex("amount"));
            dTotBalanceForAcc -= dAmount;
        }
        return dTotBalanceForAcc;

    }
    public boolean exportAllTablesToCSV() {
        DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String timestamp = df.format(new Date());

        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return false;
        } else {
            File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            try {
                SQLiteDatabase db = getReadableDatabase();

                // List of table names in your database
                String[] tableNames = {"assetLiability", "incomeExpense", "transfer", "final"};

                for (String tableName : tableNames) {
                    File file = new File(exportDir, tableName + "_" + timestamp + ".csv");
                    file.createNewFile();

                    PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                    Cursor curCSV = db.rawQuery("SELECT * FROM " + tableName, null);

                    // Write the column names as the first row in the CSV file
                    String[] columnNames = curCSV.getColumnNames();
                    printWriter.println(String.join(",", columnNames));

                    while (curCSV.moveToNext()) {
                        StringBuilder record = new StringBuilder();
                        for (String columnName : columnNames) {
                            int columnIndex = curCSV.getColumnIndex(columnName);
                            String value = curCSV.getString(columnIndex);
                            record.append(value).append(",");
                        }
                        printWriter.println(record);
                    }

                    curCSV.close();
                    printWriter.close();
                    Log.d(TAG, "Exported " + tableName + " to " + file.getAbsolutePath());
                }

                db.close();
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error exporting tables to CSV: " + e.getMessage());
                return false;
            }
        }
    }
}