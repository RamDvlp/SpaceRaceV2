package com.example.meteoriterace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class Score extends AppCompatActivity {


    private ListFragment fragmentList;
    private MapActivity mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        fragmentList = new ListFragment();

        mapFragment = new MapActivity();


        fragmentList.setLocation_callback(location_callback);


        ScreenUtils.hideSystemUI(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_LAY_frameScore, fragmentList)
                .replace(R.id.main_LAY_frameMAP, mapFragment)
                //.add(R.id.main_LAY_frame1, fragmentMap)
               // .hide(fragmentMap)
                .commit();




    }

    private Location_Callback location_callback = new Location_Callback() {
        @Override
        public void setLocationOnMap(double lat, double lon) {
            mapFragment.setLocation(lat,lon);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        appsResource.getInstance().resumeBackgroundMusic();


    }

    @Override
    protected void onStart() {
        super.onStart();

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