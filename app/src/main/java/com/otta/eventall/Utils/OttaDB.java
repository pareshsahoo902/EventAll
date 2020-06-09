package com.otta.eventall.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.otta.eventall.Activities.AddBusiness.Model.LocationModel;
import com.otta.eventall.Activities.AddBusiness.Model.OwnerModel;

public class OttaDB {

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    static void Init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("EventAllBusiness", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }

    public static void save_OwnerInfo(Context context, OwnerModel ownerModel) {
        Init(context);
        Gson gson = new Gson();
        String json = gson.toJson(ownerModel);
        editor.putString("OwnerInfo", json).apply();
    }


    public static OwnerModel get_OwnerInfo(Context context) {
        Init(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("OwnerInfo", null);
        return gson.fromJson(json, OwnerModel.class);
    }


    public static void save_TagLine(Context context, String TagLine) {
        Init(context);
        editor.putString("TAGLINE", TagLine).apply();
    }

    public static String get_TagLine(Context context){
        Init(context);
        return sharedPreferences.getString("TAGLINE",null);
    }


    public static void save_Intro(Context context, String Intro) {
        Init(context);
        editor.putString("INTRO", Intro).apply();
    }

    public static String get_Intro(Context context){
        Init(context);
        return sharedPreferences.getString("INTRO",null);
    }


    public static void save_LocationInfo(Context context, LocationModel locationModel) {
        Init(context);
        Gson gson = new Gson();
        String json = gson.toJson(locationModel);
        editor.putString("LocationInfo", json).apply();
    }


    public static LocationModel get_LocationInfo(Context context) {
        Init(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("LocationInfo", null);
        return gson.fromJson(json, LocationModel.class);
    }


}
