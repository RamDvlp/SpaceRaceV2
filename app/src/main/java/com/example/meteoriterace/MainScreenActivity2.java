package com.example.meteoriterace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainScreenActivity2 extends AppCompatActivity {

    Button newGame;
    Button sensorGame;
    Button score;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_screen2);

        initViews();
        newGame.setOnClickListener(v -> regularGame());
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