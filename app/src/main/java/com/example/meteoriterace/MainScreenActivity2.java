package com.example.meteoriterace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.gson.Gson;

public class MainScreenActivity2 extends AppCompatActivity {

    Button newGame;
    Button sensorGame;
    Button score;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenUtils.hideSystemUI(this);
        setContentView(R.layout.activity_main_screen2);

        //******
        ScoreEntery sc = new ScoreEntery(100).setLon(0).setLat(0);

        String json = new Gson().toJson(sc);
        mySP.getSP().writeScoreEntry(json, sc.getTheDate());
        //**********


        initViews();
        newGame.setOnClickListener(v -> regularGame());
        sensorGame.setOnClickListener(v -> sensoredGame());
        score.setOnClickListener(v -> scoreScreen());

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        appsResource.getInstance().resumeBackgroundMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appsResource.getInstance().releaseResource();
    }

    @Override
    protected void onPause() {
        super.onPause();
        appsResource.getInstance().pausebackgroundMusic();
    }


    private void scoreScreen() {
        intent = new Intent(MainScreenActivity2.this,Score.class);


        //intent.putExtra("gameType", "sensors");
        startActivity(intent);
    }

    private void sensoredGame() {
        intent = new Intent(MainScreenActivity2.this,MainActivity.class);
        intent.putExtra("gameType", "sensors");
        startActivity(intent);
    }

    private void regularGame() {
        intent = new Intent(MainScreenActivity2.this,MainActivity.class);
        intent.putExtra("gameType", "buttons");
        startActivity(intent);
    }

    private void initViews() {

        newGame = findViewById(R.id.mainScreen_BTN_newGame);
        sensorGame = findViewById(R.id.mainScreen_BTN_sensorGame);
        score = findViewById(R.id.mainScreen_BTN_score);
    }
}