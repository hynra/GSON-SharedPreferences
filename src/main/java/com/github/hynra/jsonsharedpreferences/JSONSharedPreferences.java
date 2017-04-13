package com.github.hynra.jsonsharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;

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
        String val = new Gson().toJson(object);
        mEditor.putString(object.getClass().getCanonicalName(), val);
    }

    public void save(Object object, String values){
        mEditor.putString(object.getClass().getCanonicalName(), values);
    }

    public void commit(){
        mEditor.commit();
    }

    public Object get(Object object){
        String val = mSharedPrefs.getString(object.getClass().getCanonicalName(), "");
        object = object.getClass();
        object = new Gson().fromJson(val, (Class<Object>) object);
        return object;
    }


}
