package com.example.meteoriterace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final int DELAY = 500;
    private static final int FAST_DELAY = 1000;
    private static final float SENSOR_THRESHOLD = 3;

    private int livescount = 3;
    private Timer timer;
    private int score = 0;
    private MaterialTextView score_LBL;

    private MaterialButton btn_Left;
    private MaterialButton btn_Right;
    private AppCompatImageView backGround;

    private AppCompatImageView[][] meteorites;

    private AppCompatImageView[] lives;

    private AppCompatImageView[] rocketShips;

    private GameRules gameRules = new GameRules(new Random());

    private String gameType;

    private Sensors mySensors;

    private TimerTask timeTask;

    private boolean state = false;

    private double lon, lat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenUtils.hideSystemUI(this);
        setContentView(R.layout.activity_main);

        //v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        gameType = getIntent().getStringExtra("gameType");


        if(gameType.equals("sensors")){

            mySensors = new Sensors(this, new Sensors.CallBack_ScreenTilt() {
                @Override
                public void leftRightTilt(float x) {
                    if(x > 2*SENSOR_THRESHOLD)
                            gameRules.moveRocket(true);
                    if(x > SENSOR_THRESHOLD && x < 2*SENSOR_THRESHOLD)
                        gameRules.moveRocket(true);

                    if(x < 2*(-SENSOR_THRESHOLD))
                        gameRules.moveRocket(false);
                    if(x < (-SENSOR_THRESHOLD) && x > 2*(-SENSOR_THRESHOLD))
                        gameRules.moveRocket(false);

                    updateRocketUI();

                }

                @Override
                public void frontTilt(float y) {


                    Log.d("yyyy", ""+y);



                        if (y < 0 ) {
                            stopTimer();
                            startTimer(FAST_DELAY);
                            state = true;
                        } else {
                            state = false;
                        }


                }
            });

        }

        initViews();
        setTick();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(gameType.equals("sensors"))
            mySensors.start();
        //startTimer();
        //appsResource.getInstance().startBackgroundMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if(gameType.equals("buttons"))
            startTimer(DELAY);
        appsResource.getInstance().startBackgroundMusic();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(gameType.equals("sensors"))
            mySensors.stop();

        //stopTimer();
        //appsResource.getInstance().releaseResource();
        //appsResource.getInstance().pausebackgroundMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
        appsResource.getInstance().pausebackgroundMusic();
    }

    private void setTick() {


        gameRules.updateGameMesh();

        if (gameRules.isCoinColected()) {
            if(state)
                score += 20;
            else
                score += 10;
            String displayScore = String.valueOf(score);
            score_LBL.setText(displayScore);

        }

        int[][] rocklocs = gameRules.getGameMesh();
        for (int i = 0; i < meteorites.length; i++) {
            for (int k = 0; k < meteorites[0].length; k++) {
                if (rocklocs[i][k] == GameRules.ROCK) {
                    meteorites[i][k].setImageResource(R.drawable.meteore);
                    meteorites[i][k].setVisibility(View.VISIBLE);
                }
                if (rocklocs[i][k] == GameRules.COIN) {
                    meteorites[i][k].setImageResource(R.drawable.ic_coin);
                    meteorites[i][k].setVisibility(View.VISIBLE);
                }
                if (rocklocs[i][k] == GameRules.NOTHING) {
                    meteorites[i][k].setVisibility(View.INVISIBLE);
                }
            }
        }

        if (gameRules.isColition()) {
            preformColition();

        } else {
            btn_Right.setEnabled(true);
            btn_Left.setEnabled(true);

            postColition();
        }


    }

    private void postColition() {
        rocketShips[colitionPlace].setImageResource(R.drawable.rocket);
    }


    private void startTimer(int delay) {

        if(timer != null) {
            timer.cancel();
            timer = null;

        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTick();
                    }
                });
            }
        },0, delay);
    }

    private void stopTimer() {
        if(timer!=null)
            timer.cancel();
    }

    private void initViews() {

        backGround = findViewById(R.id.main_img_back);

        Glide.with(MainActivity.this)
                .load(R.drawable.night_sky)
                .into(backGround);

        score_LBL = findViewById(R.id.game_LBL_score);
        meteorites = new AppCompatImageView[][]{
                {findViewById(R.id.main_IMG_meteor_00), findViewById(R.id.main_IMG_meteor_01), findViewById(R.id.main_IMG_meteor_02), findViewById(R.id.main_IMG_meteor_03), findViewById(R.id.main_IMG_meteor_04)},
                {findViewById(R.id.main_IMG_meteor_10), findViewById(R.id.main_IMG_meteor_11), findViewById(R.id.main_IMG_meteor_12), findViewById(R.id.main_IMG_meteor_13), findViewById(R.id.main_IMG_meteor_14)},
                {findViewById(R.id.main_IMG_meteor_20), findViewById(R.id.main_IMG_meteor_21), findViewById(R.id.main_IMG_meteor_22), findViewById(R.id.main_IMG_meteor_23), findViewById(R.id.main_IMG_meteor_24)},
                {findViewById(R.id.main_IMG_meteor_30), findViewById(R.id.main_IMG_meteor_31), findViewById(R.id.main_IMG_meteor_32), findViewById(R.id.main_IMG_meteor_33), findViewById(R.id.main_IMG_meteor_34)},
                {findViewById(R.id.main_IMG_meteor_40), findViewById(R.id.main_IMG_meteor_41), findViewById(R.id.main_IMG_meteor_42), findViewById(R.id.main_IMG_meteor_43), findViewById(R.id.main_IMG_meteor_44)},
                {findViewById(R.id.main_IMG_meteor_50), findViewById(R.id.main_IMG_meteor_51), findViewById(R.id.main_IMG_meteor_52), findViewById(R.id.main_IMG_meteor_53), findViewById(R.id.main_IMG_meteor_54)},
                {findViewById(R.id.main_IMG_meteor_60), findViewById(R.id.main_IMG_meteor_61), findViewById(R.id.main_IMG_meteor_62), findViewById(R.id.main_IMG_meteor_63), findViewById(R.id.main_IMG_meteor_64)},
                {findViewById(R.id.main_IMG_meteor_70), findViewById(R.id.main_IMG_meteor_71), findViewById(R.id.main_IMG_meteor_72), findViewById(R.id.main_IMG_meteor_73), findViewById(R.id.main_IMG_meteor_74)},
                {findViewById(R.id.main_IMG_meteor_80), findViewById(R.id.main_IMG_meteor_81), findViewById(R.id.main_IMG_meteor_82), findViewById(R.id.main_IMG_meteor_83), findViewById(R.id.main_IMG_meteor_84)}};

        for (int i = 0; i < meteorites.length; i++) {
            for (int j = 0; j < meteorites[0].length; j++) {
                meteorites[i][j].setVisibility(View.INVISIBLE);
            }
        }


        lives = new AppCompatImageView[]{findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3)};

        rocketShips = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_rocket1),
                findViewById(R.id.main_IMG_rocket2),
                findViewById(R.id.main_IMG_rocket3),
                findViewById(R.id.main_IMG_rocket4),
                findViewById(R.id.main_IMG_rocket5)};

        btn_Left = findViewById(R.id.main_BTN_left);
        btn_Right = findViewById(R.id.main_BTN_right);

        if(gameType.equals("buttons")) {
            btn_Left.setOnClickListener(view -> moveRocket(true));
            btn_Right.setOnClickListener(view -> moveRocket(false));
        } else {
            btn_Left.setEnabled(false);
            btn_Right.setEnabled(false);
            btn_Left.setVisibility(View.INVISIBLE);
            btn_Right.setVisibility(View.INVISIBLE);
        }
    }

    private void moveRocket(boolean left) {


        gameRules.moveRocket(left);
        updateRocketUI();

    }

    private int colitionPlace = 0;

    private void preformColition() {

        colitionPlace = gameRules.getCurrentRocketLocation();
        rocketShips[colitionPlace].setImageResource(R.drawable.explosion);
        livescount--;

        if(livescount<0)
            return;

        try {
            lives[livescount].setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /// v.vibrate(DELAY);
        appsResource.getInstance().vibrate();
        if (livescount == 0)
            gameOver();
    }

    private void gameOver() {
        saveScore();
        onPause();
        btn_Right.setEnabled(false);
        btn_Left.setEnabled(false);
        //v.cancel();
        //appsResource.getInstance().releaseResource();
        //appsResource.getInstance().cancelVibrator();
        appsResource.getInstance().toast("GAME OVER");

       // loadScore();
        finish();

    }
//
//    private void loadScore() {
////TODO - temp load test, move to score activity
//        String json = mySP.getSP().readScoreEntery();
//        ScoreEntery sc;
//
//        sc = new Gson().fromJson(json,ScoreEntery.class);
//
//        Log.d("asd", sc.toString());
//        Log.d("asd", sc.getTheDate());
//        Log.d("asd", String.valueOf(sc.getScore()));
//
//    }

    private void saveScore() {

        Random rn = new Random();
        lat = rn.nextDouble()*(-1);
        lon = rn.nextDouble()*(-1);


        ScoreEntery sc = new ScoreEntery(score).setLat(lat*90).setLon(lon*180);


        String json = new Gson().toJson(sc);
        mySP.getSP().writeScoreEntry(json,sc.getTheDate());

    }

    private void updateRocketUI() {
        int[] currentRocketLoc = gameRules.getRocket();
        for (int i = 0; i < rocketShips.length; i++) {
            if (currentRocketLoc[i] == GameRules.ROCKET) {
                rocketShips[i].setVisibility(View.VISIBLE);

            } else {
                rocketShips[i].setVisibility(View.INVISIBLE);
            }
        }
    }


}