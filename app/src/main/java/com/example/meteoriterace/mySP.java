package com.example.meteoriterace;

import android.content.Context;
import android.content.SharedPreferences;

public class mySP {

    private SharedPreferences pref = null;

    private static final String FILE_NAME = "GAME_SCORE";

    private static mySP sp;

    private static final String DEF_VALUE = "";


    private mySP(Context context) {
        this.pref = context.getApplicationContext().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
    }

    public static void init(Context context){
        if(sp == null){
            sp = new mySP(context);
        }
    }

    public static mySP getSP(){
        return sp;
    }

    public void writeScoreEntry(String scoreEntery){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(ScoreEntery.KEY_ENTERY,scoreEntery);
        editor.apply();
    }

    public String readScoreEntery(){
        String entery = pref.getString(ScoreEntery.KEY_ENTERY, DEF_VALUE);
        return entery;


    }






}
