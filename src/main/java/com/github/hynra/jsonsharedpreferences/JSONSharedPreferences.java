package com.github.hynra.jsonsharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hynra on 4/13/17.
 */

public class JSONSharedPreferences {

    private Context context;
    private String mPreferencesName;
    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mEditor;

    public JSONSharedPreferences(Context context, String mPreferencesName){
        this.context = context;
        this.mPreferencesName = mPreferencesName;
        mSharedPrefs = context.getSharedPreferences(mPreferencesName, Context.MODE_PRIVATE);
        mEditor = mSharedPrefs.edit();
    }
}
