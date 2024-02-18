package com.example.budget;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    public static String getTodayDate(){
        return sdf.format(new Date());
    }
}
