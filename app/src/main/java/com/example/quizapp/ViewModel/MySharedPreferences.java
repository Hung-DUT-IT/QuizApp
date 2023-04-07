package com.example.quizapp.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.quizapp.Model.Entity.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MySharedPreferences {
    private static final String PREFS_NAME = "MyPrefs";
    private static MySharedPreferences instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final Gson gson;
    private MySharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public static MySharedPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new MySharedPreferences(context);
        }
        return instance;
    }
    public void saveQuestion(String key, List<Question> questions){
        String json = gson.toJson(questions);
        editor.putString(key, json).apply();
        editor.commit();
    }
    public List<Question> getQuestion(String key){
        String json = sharedPreferences.getString(key, "");
        Type type = new TypeToken<List<Question>>() {}.getType();
        return gson.fromJson(json, type);
    }
    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    // Add more methods for other data types as needed
}
