```java
package com.example;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String jsonObjectString = "{\n" +
            "  \"user_id\": 666,\n" +
            "  \"user_name\": \"hynra\",\n" +
            "  \"website\" : \"http://hynra.com\"\n" +
            "  }";

    String jsonArrayString = "[{\n" +
            "\t\t\"user_id\": 667,\n" +
            "\t\t\"user_name\": \"blog\",\n" +
            "\t\t\"website\": \"http://blog.hynra.com\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"user_id\": 668,\n" +
            "\t\t\"user_name\": \"devnote\",\n" +
            "\t\t\"website\": \"http://devnote.hynra.com\"\n" +
            "\t}\n" +
            "]";

    JSONObject jsonObject;
    JSONArray jsonArray;

    GSONSharedPreferences gsonSharedPreferences;
    private Context context;
    private Profile mProfile;
    private Profile[] mProfiles;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        gsonSharedPreferences = new GSONSharedPreferences(context);

        try {
            jsonObject = new JSONObject(jsonObjectString);
            jsonArray = new JSONArray(jsonArrayString);

            // save & get from object
            mProfile = new Gson().fromJson(jsonObjectString, Profile.class);
            gsonSharedPreferences.saveObject(mProfile);
            try {
                Profile profile = (Profile) gsonSharedPreferences.getObject(new Profile());
                Log.i(TAG, profile.getUserName());
                // get json object
                JSONObject object = gsonSharedPreferences.getJsonObject(new Profile());
                Log.i(TAG, object.toString());
            } catch (ParsingException e) {
                e.printStackTrace();
            }


            // save & get from json object
            gsonSharedPreferences.saveObject(jsonObject);
            try {
                Profile profile = (Profile) gsonSharedPreferences.getObject(new Profile());
                Log.i(TAG, profile.getWebsite());
                // get json string
                Log.i(TAG, gsonSharedPreferences.getJsonObjectString(new Profile()));
            } catch (ParsingException e) {
                e.printStackTrace();
            }

            // save & get from json string
            gsonSharedPreferences.saveObject(jsonObjectString);
            try {
                Profile profile = (Profile) gsonSharedPreferences.getObject(new Profile());
                Log.i(TAG, "id: "+profile.getUserId());
            } catch (ParsingException e) {
                e.printStackTrace();
            }



            // save & get from objects
            mProfiles = new Profile[jsonArray.length()];
            for(int i = 0; i < mProfiles.length; i++){
                mProfiles[i] = new Gson().fromJson(jsonArray.get(i).toString(), Profile.class);
            }
            gsonSharedPreferences.saveObjects(mProfiles);
            try {
                Profile[] profiles = (Profile[]) gsonSharedPreferences.getObjects(new Profile());
                Log.i(TAG, profiles[1].getUserName());
                // get json array
                JSONArray array = gsonSharedPreferences.getJsonArray(new Profile());
                Log.i(TAG, array.toString());
            } catch (ParsingException e) {
                e.printStackTrace();
            }

            // save & get from json array
            gsonSharedPreferences.saveObjects(new Profile(), jsonArray);
            try {
                Profile[] profiles = (Profile[]) gsonSharedPreferences.getObjects(new Profile());
                Log.i(TAG, profiles[1].getWebsite());
                // get json array string
                Log.i(TAG, gsonSharedPreferences.getJsonArrayString(new Profile()));
            } catch (ParsingException e) {
                e.printStackTrace();
            }

            // save & get from json array string
            gsonSharedPreferences.saveObjects(new Profile(), jsonArrayString);
            try {
                Profile[] profiles = (Profile[]) gsonSharedPreferences.getObjects(new Profile());
                Log.i(TAG, "id : "+profiles[1].getUserId());
            } catch (ParsingException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

```
