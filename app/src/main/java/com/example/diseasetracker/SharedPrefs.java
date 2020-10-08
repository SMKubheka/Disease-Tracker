package com.example.diseasetracker;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPrefs {

    public static String KEY_EMAIL = "email";
    public static String KEY_PASSWORD = "password";
    public static String KEY_ID = "id";
    public static String KEY_STATUS = "status";
    public static String KEY_FNAME = "fname";
    public static String KEY_LNAME = "lname";


    public static boolean saveFName(Context ctx, String fname) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_FNAME, fname);
        editor.apply();
        return true;
    }

    public static String getFName(Context ctx) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        String status = sharedPref.getString(KEY_FNAME, null);

        return status;
    }

    public static boolean saveLName(Context ctx, String lname) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_LNAME, lname);
        editor.apply();
        return true;
    }

    public static String getLName(Context ctx) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        String status = sharedPref.getString(KEY_LNAME, null);

        return status;

    }

    public static boolean saveEmail(Context ctx, String email) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
        return true;
    }

    public static String getEmail(Context ctx){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        String email = sharedPref.getString(KEY_EMAIL, null);

        return email;
    }

    public static boolean savePassword(Context ctx, String password){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
        return true;
    }

    public static String getPassword(Context ctx){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPref.getString(KEY_PASSWORD, null);
    }

    public static boolean saveID(Context ctx, int ID){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(KEY_ID, ID);
        editor.apply();
        return true;
    }

    public static int getID(Context ctx){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPref.getInt(KEY_ID, 0);
    }

    public static boolean saveStatus(Context ctx, String status) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_STATUS, status);
        editor.apply();
        return true;
    }

    public static String getStatus(Context ctx){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        String status = sharedPref.getString(KEY_STATUS, null);

        return status;
    }
}
