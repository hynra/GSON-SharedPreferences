package com.github.hynra.gsonsharedpreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by hynra on 4/13/17.
 */

public class GSONSharedPreferences {

    private Context context;
    private String mPreferencesName;
    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mEditor;

    public GSONSharedPreferences(Context context, String mPreferencesName){
        this.context = context;
        this.mPreferencesName = mPreferencesName;
        mSharedPrefs = context.getSharedPreferences(mPreferencesName, Context.MODE_PRIVATE);
        mEditor = mSharedPrefs.edit();
    }


    public GSONSharedPreferences(Context context, String mPreferencesName, int mode){
        this.context = context;
        this.mPreferencesName = mPreferencesName;
        mSharedPrefs = context.getSharedPreferences(mPreferencesName, mode);
        mEditor = mSharedPrefs.edit();
    }


    public GSONSharedPreferences(Context context){
        this.context = context;
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
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

    public Object get(Object object) throws ParsingException{
        try {
            String val = mSharedPrefs.getString(object.getClass().getCanonicalName(), "");
            object = object.getClass();
            object = new Gson().fromJson(val, (Class<Object>) object);
        }catch (JsonSyntaxException exception){
            throw new ParsingException(exception.getMessage());
        }
        return object;
    }

    public JSONObject getJson(Object object) throws ParsingException{
        String val = mSharedPrefs.getString(object.getClass().getCanonicalName(), "");
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(val);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new ParsingException(e.getMessage());
        }
        return jsonObject;
    }


}
