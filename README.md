# GSON SharedPreferences

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-GSON--SharedPreferences-blue.svg?style=flat)](https://android-arsenal.com/details/1/5614)

*Save your Gson object to Android Shared Preferences*

Imagine you have plenty of static data in JSON, and you have to save it to application state. Then this lib is suitable for you.

## Add to your Project

* on your `build.gradle` root :

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
  
  * Add dependency
  
  ```
  	dependencies {
		compile 'com.github.hynra:GSON-SharedPreferences:1.0'
	}
  ```

## How To

* prepare or generate your object via `http://www.jsonschema2pojo.org/`, `https://github.com/joelittlejohn/jsonschema2pojo`, etc.

example : 

```json
{
  "user_id": 666,
  "user_name": "hynra",
  "website" : "http://hynra.com"
  }
  ```
  
  Gson object :
  
  ```java
  package com.example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("user_name")
@Expose
private String userName;
@SerializedName("website")
@Expose
private String website;

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
}

public String getUserName() {
return userName;
}

public void setUserName(String userName) {
this.userName = userName;
}

public String getWebsite() {
return website;
}

public void setWebsite(String website) {
this.website = website;
}

}
  ```
  
  * Save your object :
  
  **From existing Gson**
  
  ```java
  Profile profile = new Gson().fromJson("<your-json-string-obj>", Profile.class);
  GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(context, "<your-prefs-name>");
  gsonSharedPrefs.save(profile);
  ```
  
  **with json string**
  
  ```java
  GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(context, "<your-prefs-name>");
  gsonSharedPrefs.save(new Profile(), "<your-json-string-obj>");
  ```
  
  **with json object**
  
  ```java
  GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(context, "<your-prefs-name>");
  gsonSharedPrefs.save(new Profile(), "<your-json-object>");
  ```
  
  * Get your object :
  
  ```java
  GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(context, "<your-prefs-name>");
  Profile profile = null;
  try {
    profile = (Profile) gsonSharedPrefs.get(new Profile());
    Log.i("test", profile.getUserName());
  } catch (ParsingException e) {
    e.printStackTrace();
  }
  ```
  
  must be wrap with `ParsingException` to avoid parsing error.
  
  * need `SharedPreferences` object ?
  
  ```java
  SharedPreferences prefs = gsonSharedPrefs.getSharedPreferences;
  ```
  
