package com.example.budget;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPrefHandler {
    public static final String SHARED_PREF_NAME = "BUDGET_SHARED_PREF";
    public static final String TAGS = "TAGS";
    public static final String ACCOUNTS = "ACCOUNTS";

    public static void saveData(Context ctx, String strType, String strData)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        ArrayList<String> arrayList = loadData(ctx, strType);
        arrayList.add(strData);

        String strJson = gson.toJson(arrayList);
        editor.putString(strType, strJson);
        editor.apply();

    }

    public static ArrayList<String> loadData(Context ctx, String strType)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String strJson = sharedPreferences.getString(strType, null);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        if (strJson != null) {
            return gson.fromJson(strJson, type);
        }else{
            return new ArrayList<String>();
        }
    }

    public static void clear(Context ctx)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
