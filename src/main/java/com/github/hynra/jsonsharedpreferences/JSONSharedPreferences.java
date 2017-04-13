package com.github.hynra.jsonsharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.json.JSONObject;


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
        commit();
    }

    public void save(Object object, String values){
        mEditor.putString(object.getClass().getCanonicalName(), values);
        commit();
    }


    public SharedPreferences getSharedPreferences(){
        return mSharedPrefs;
    }


    public void save(Object object, JSONObject values){
        mEditor.putString(object.getClass().getCanonicalName(), values.toString());
        commit();
    }

    public void commit(){
        mEditor.commit();
    }

    public Object get(Object object) throws Exception{
        try {
            String val = mSharedPrefs.getString(object.getClass().getCanonicalName(), "");
            object = object.getClass();
            object = new Gson().fromJson(val, (Class<Object>) object);
        }catch (JsonSyntaxException exception){
            throw new JsonSyntaxException(exception);
        }

        return object;
    }


}
