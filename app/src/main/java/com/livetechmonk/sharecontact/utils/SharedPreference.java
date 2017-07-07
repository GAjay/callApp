package com.livetechmonk.sharecontact.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author: Ajay
 * */

public class SharedPreference {
    private static final int MODE_PRIVATE=0;
    private final SharedPreferences preferences;

    public SharedPreference(Context context){
        String userPref = "callAPP";
        preferences=context.getSharedPreferences(userPref,MODE_PRIVATE);
    }

    public void storeDeviceToken(int token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ReferenceToken", token);
        editor.commit();
    }
    public int getDeviceToken(){

        return preferences.getInt("ReferenceToken",0);
    }
}
