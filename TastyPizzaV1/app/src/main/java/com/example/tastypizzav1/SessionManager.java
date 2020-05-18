package com.example.tastypizzav1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String SURNAME = "SURNAME";
    public static final String CITY= "CITY";
    public static final String STREET = "STREET";
    public static final String APARTMENT_NUMBER = "APARTMENT_NUMBER";
    public static final String POSTCODE = "POSTCODE";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String RESTAURANT_ID = "RESTAURANT_ID";
    public SessionManager(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }

    public void createSession(String name,String email,String surname, String city,String street,String apartment_number, String postcode, String phone_number)
    {
        editor.putBoolean(LOGIN,true);
        editor.putString(NAME,name);
        editor.putString(EMAIL,email);
        editor.putString(SURNAME,surname);
        editor.putString(CITY,city);
        editor.putString(STREET,street);
        editor.putString(APARTMENT_NUMBER,apartment_number);
        editor.putString(POSTCODE,postcode);
        editor.putString(PHONE_NUMBER,phone_number);
        editor.apply();
    }

    public void createSession(String name,String restaurant_id)
    {
        editor.putBoolean(LOGIN,true);
        editor.putString(NAME,name);
        editor.putString(RESTAURANT_ID,restaurant_id);
        editor.apply();
    }


    public boolean isLogin()
    {
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void checkLogin()
    {
        if(!this.isLogin())
        {
            Intent i = new Intent(context,MainActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail()
    {
        HashMap<String,String> user = new HashMap<>();
        user.put(NAME,sharedPreferences.getString(NAME,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(SURNAME,sharedPreferences.getString(SURNAME,null));
        user.put(CITY,sharedPreferences.getString(CITY,null));
        user.put(STREET,sharedPreferences.getString(STREET,null));
        user.put(APARTMENT_NUMBER,sharedPreferences.getString(APARTMENT_NUMBER,null));
        user.put(POSTCODE,sharedPreferences.getString(POSTCODE,null));
        user.put(PHONE_NUMBER,sharedPreferences.getString(PHONE_NUMBER,null));
        user.put(RESTAURANT_ID,sharedPreferences.getString(RESTAURANT_ID,null));
        return user;
    }

    public void logout()
    {
        editor.clear();
        editor.commit();
    }
}
