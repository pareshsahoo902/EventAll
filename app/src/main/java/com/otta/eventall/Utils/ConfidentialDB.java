package com.otta.eventall.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfidentialDB {

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    static void Init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("EventAllAppSettings", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }

    public static void save_AccessToken(Context context, String VeryLongToken) {
        Init(context);
        editor.putString("AccessToken", VeryLongToken).apply();
    }

    public static String get_AccessToken(Context context) {
        Init(context);
        return sharedPreferences.getString("AccessToken" , null);
    }

    public static void save_Email(Context context , String EmailAddress){
        Init(context);
        editor.putString("ConfigEmail", EmailAddress).apply();
    }

    public static  void save_Password(Context context , String Password){
        Init(context);
        editor.putString("ConfigPassword", Password).apply();
    }

    public static String get_Email(Context context) {
        Init(context);
        return sharedPreferences.getString("ConfigEmail" , null);
    }

    public static String get_Password(Context context) {
        Init(context);
        return sharedPreferences.getString("ConfigPassword" , null);
    }


    public static void save_logStatus(Context context , boolean isLoggedIn){
        Init(context);
        editor.putBoolean("loggedIn" , isLoggedIn).apply();
    }

    public static boolean get_logStatus(Context context){
        Init(context);
        return sharedPreferences.getBoolean("loggedIn",false);
    }

}
