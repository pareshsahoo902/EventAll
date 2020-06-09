package com.otta.eventall.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserDetailsDB {


    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    static void Init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }

    public static void save_UserName(Context context, String Username) {
        Init(context);
        editor.putString("Username", Username).apply();
    }

    public static String get_Username(Context context){
        Init(context);
        return sharedPreferences.getString("Username",null);
    }

    public static void save_UserEmailAddress(Context context, String Username) {
        Init(context);
        editor.putString("Email", Username).apply();
    }

    public static String get_UserEmailAddress(Context context){
        Init(context);
        return sharedPreferences.getString("Email",null);
    }


    public static void save_ProfilePictureURL(Context context, String URL) {
        Init(context);
        editor.putString("ProPicURL", URL).apply();
    }

    public static String get_ProfilePictureURL(Context context){
        Init(context);
        return sharedPreferences.getString("ProPicURL",null);
    }


}
