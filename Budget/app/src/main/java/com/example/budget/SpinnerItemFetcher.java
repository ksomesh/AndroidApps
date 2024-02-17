package com.example.budget;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class SpinnerItemFetcher {
    public static final String TAGS =  "TAGS";
    public static final String ACCOUNTS = "ACCOUNTS";

    public static final ArrayList<String> arrListTag = new ArrayList<String>(Arrays.asList("Select Tag", "Salary",
            "Interest", "Savings", "Bill and utility", "Grocery", "Investment", "Food and dining", "Shopping", "Misc",
            "Health", "Travel", "Loan Emi", "Start balance"));

    public static final ArrayList<String> arrListAccount = new ArrayList<String>(Arrays.asList("Select Account", "Cash"));


    public static ArrayAdapter<String> fetchSpinnerItem(Context ctx, String strType)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        if(strType.equals(TAGS)){
            arrayList.addAll(arrListTag);
        }else {
            arrayList.addAll(arrListAccount);
        }
        arrayList.addAll(SharedPrefHandler.loadData(ctx, strType));

        //remove duplicates
        Set<String> setList = new LinkedHashSet<String>(arrayList);
        arrayList.clear();
        arrayList.addAll(setList);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }
}
