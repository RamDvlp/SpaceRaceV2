package com.example.meteoriterace;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Stack;

public class mySP {

    private SharedPreferences pref = null;

    private static final String FILE_NAME = "GAME_SCORE";

    private static mySP sp;

    private static final String DEF_VALUE = "";

    private ArrayList<ScoreEntery> allScores;

    private ArrayList<String> allKeys;

    private mySP(Context context) {
        this.pref = context.getApplicationContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        allKeys = new ArrayList<>();
    }

    public static void init(Context context) {
        if (sp == null) {
            sp = new mySP(context);
        }

    }

    public static mySP getSP() {
        return sp;
    }

    public void writeScoreEntry(String scoreEntery, String theDate) {
        allKeys.add(theDate);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(theDate, scoreEntery);
        editor.apply();
    }

    public ScoreEntery readScoreEntery() {
        String entery = pref.getString(allKeys.iterator().next(), DEF_VALUE);

        ScoreEntery sc = new Gson().fromJson(entery,ScoreEntery.class);
        return sc;

    }

    public ScoreEntery[] readAll() {

        Map<String, ?> allEntries = pref.getAll();

        allScores = new ArrayList<ScoreEntery>();


        // Iterate over the entries and display them
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();

            // Check if the key matches the desired pattern
            if (allKeys.contains(key)) {


                ScoreEntery sc = new Gson().fromJson((String) entry.getValue(),ScoreEntery.class);

                //Object value = entry.getValue();
                // Process the entry as needed

                    allScores.add(sc);

                   // Toast.makeText(this, "Key: " + key + ", Value: " + intValue, Toast.LENGTH_SHORT).show();
                }
                // Add more conditions for other data types if needed
            }
        return allScores.toArray(new ScoreEntery[0]);
        }



    }






