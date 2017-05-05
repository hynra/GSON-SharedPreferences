package com.github.hynra.gsonsharedpreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


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



    /** save objects from objects  **/
    public void save(Object[] objects){
        String[] vals = new String[objects.length];
        for(int i = 0; i <objects.length; i++){
            vals[i] = new Gson().toJson(objects[i]);
            mEditor.putString(objects[i].getClass().getCanonicalName()+i, vals[i]);
        }
        mEditor.putInt(objects.getClass().getCanonicalName(), objects.length);
        commit();
    }


    /** save objects from json array  **/
    public void saveObjects(Object object, JSONArray array){
        try {
            String[] vals = new String[array.length()];
            for(int i = 0; i <vals.length; i++){

                    vals[i] = new Gson().toJson(array.getJSONObject(i).toString());
                mEditor.putString(object.getClass().getCanonicalName()+i, vals[i]);
            }
            mEditor.putInt(object.getClass().getCanonicalName(), array.length());
            commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /** save objects from json array string  **/
    public void saveObjects(Object object, String jArrayString){
        try {
            JSONArray array = new JSONArray(jArrayString);
            String[] vals = new String[array.length()];
            for(int i = 0; i <vals.length; i++){

                vals[i] = new Gson().toJson(array.getJSONObject(i).toString());
                mEditor.putString(object.getClass().getCanonicalName()+i, vals[i]);
            }
            mEditor.putInt(object.getClass().getCanonicalName(), array.length());
            commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**  save object from object **/
    public void saveObject(Object object){
        String val = new Gson().toJson(object);
        mEditor.putString(object.getClass().getCanonicalName(), val);
        commit();
    }

    /**  save object from string **/
    public void saveObject(Object object, String values){
        mEditor.putString(object.getClass().getCanonicalName(), values);
        commit();
    }

    /**  save object from json object **/
    public void saveObject(Object object, JSONObject values){
        mEditor.putString(object.getClass().getCanonicalName(), values.toString());
        commit();
    }

    public SharedPreferences getSharedPreferences(){
        return mSharedPrefs;
    }



    private void commit(){
        mEditor.commit();
    }

    /**  get object **/
    public Object getObject(Object object) throws ParsingException{
        try {
            String val = mSharedPrefs.getString(object.getClass().getCanonicalName(), "");
            object = object.getClass();
            object = new Gson().fromJson(val, (Class<Object>) object);
        }catch (JsonSyntaxException exception){
            throw new ParsingException(exception.getMessage());
        }
        return object;
    }


    /**  get objects **/
    public Object[] getObjects(Object object) throws ParsingException{
        Object[] objects = null;
        try {
            int size = mSharedPrefs.getInt(object.getClass().getCanonicalName(), 0);
            objects = new Object[size];
            String[] vals = new String[size];
            for(int i = 0; i < size; i++){
                vals[i] = mSharedPrefs.getString(objects[i].getClass().getCanonicalName()+i, "");
                objects[i] = objects[i].getClass();
                objects[i] = new Gson().fromJson(vals[i], (Class<Object>) objects[i]);
            }

        }catch (JsonSyntaxException exception){
            throw new ParsingException(exception.getMessage());
        }
        return objects;
    }


    /**  get json object **/
    public JSONObject getJsonObject(Object object) throws ParsingException{
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


    /**  get json object string **/
    public String getJsonObjectString(Object object) throws ParsingException{
        String val = "";
        try {
            val = mSharedPrefs.getString(object.getClass().getCanonicalName(), "");
        }catch (JsonSyntaxException exception){
            throw new ParsingException(exception.getMessage());
        }
        return val;
    }


    /**  get json array **/
    public JSONArray getJsonArray(Object object) throws ParsingException{
        int size = mSharedPrefs.getInt(object.getClass().getCanonicalName(), 0);
        String[] vals = new String[size];
        JSONArray jsonArray = new JSONArray();
        try {
            for(int i =0; i < size; i++){
                JSONObject jsonObject = new JSONObject(mSharedPrefs.getString(object.getClass().getCanonicalName()+i, ""));
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new ParsingException(e.getMessage());
        }
        return jsonArray;
    }


    /**  get json array string **/
    public String getJsonArrayString(Object object) throws ParsingException{
        int size = mSharedPrefs.getInt(object.getClass().getCanonicalName(), 0);
        String[] vals = new String[size];
        JSONArray jsonArray = new JSONArray();
        try {
            for(int i =0; i < size; i++){
                JSONObject jsonObject = new JSONObject(mSharedPrefs.getString(object.getClass().getCanonicalName()+i, ""));
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new ParsingException(e.getMessage());
        }
        return jsonArray.toString();
    }


}
