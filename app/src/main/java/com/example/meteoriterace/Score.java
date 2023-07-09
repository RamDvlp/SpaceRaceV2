package com.example.meteoriterace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Score extends AppCompatActivity {


    private ListFragment fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        fragmentList = new ListFragment();

        ScreenUtils.hideSystemUI(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_LAY_frameScore, fragmentList)
               // .add(R.id.main_LAY_frameScore, fragmentList)
                //.add(R.id.main_LAY_frame1, fragmentMap)
               // .hide(fragmentMap)
                .commit();




    }

    @Override
    protected void onStart() {
        super.onStart();
        appsResource.getInstance().resumeBackgroundMusic();


        //fragmentList.setScore(mySP.getSP().readScoreEntery());

    }


    private void changeFrame() {
        getSupportFragmentManager().beginTransaction()
                //.hide(fragmentList)
                //.show(fragmentMap)
                .commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        appsResource.getInstance().pausebackgroundMusic();

    }
}