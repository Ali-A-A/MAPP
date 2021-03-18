package com.example.mapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mapp.models.TodoList;
import com.google.gson.Gson;



public class MyPreferenceManager {
    private static MyPreferenceManager ins = null;
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    public static MyPreferenceManager getIns(Context context) {
        if(ins == null) {
            ins = new MyPreferenceManager(context);
        }
        return ins;
    }

    private MyPreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences("my_preferences" , context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setWorks(TodoList todos) {
        Gson gson = new Gson();
        String todosJson = gson.toJson(todos , TodoList.class);
        editor.putString("todos" , todosJson).apply();
    }

    public TodoList getWorks() {
        Gson gson = new Gson();
        String todoStr = sharedPreferences.getString("todos" , null);
        if(todoStr == null) return new TodoList();
        TodoList todos = gson.fromJson(todoStr , TodoList.class);
        return todos;
    }
}
