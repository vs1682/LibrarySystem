package com.inatreo.testing.librarysystem.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vishal on 2/6/2016.
 */
public class PreferenceManager {
    private SharedPreferences mSharedPreferences;
    private static PreferenceManager preferenceManager;
    private static final String PREFERENCE_NAME = "com.inatrea.testing.librarysystem.MY_PREF_FILE";

    private PreferenceManager(Context context){
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }
    public static PreferenceManager getInstance(Context context){
        if (preferenceManager == null)
            preferenceManager = new PreferenceManager(context);
        return preferenceManager;
    }

    public void putString(String key, String value){
        mSharedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    public String getString(String key){
        return mSharedPreferences.getString(key, " ");
    }

    public int getInt(String key){
        return mSharedPreferences.getInt(key, 0);
    }

    public boolean contains(String key){
        return mSharedPreferences.contains(key);
    }

    public void remove(String key){
        mSharedPreferences.edit()
                .remove(key)
                .apply();
    }

    public void clearEverything(String key){
        mSharedPreferences.edit()
                .clear()
                .apply();
    }
}
