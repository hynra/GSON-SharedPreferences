package com.github.hynra.jsonsharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

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

    public void save(Object object){
        String val = new Gson().toJson(object.getClass());
        mEditor.putString(object.getClass().getCanonicalName(), val);
    }

    public void save(Object object, String values){
        mEditor.putString(object.getClass().getCanonicalName(), values);
    }

    public void commit(){
        mEditor.commit();
    }

}
