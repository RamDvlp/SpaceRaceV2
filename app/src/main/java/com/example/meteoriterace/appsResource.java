package com.example.meteoriterace;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class appsResource {



    private Context context;
    private Vibrator vibrator;

    private MediaPlayer mediaPlayer;

    //private mySP mysp;


    private static appsResource mySignal;

    private appsResource(@NonNull Context context) {
        this.context = context;
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(context, R.raw.music_for_arcade_style_game);

        //mySP.init(context);
        //startBackgroundMusic();
    }

    public void startBackgroundMusic() {
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void stopMusic(){
        mediaPlayer.release();
    }

    public void pausebackgroundMusic(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    public void resumeBackgroundMusic(){
        if(!mediaPlayer.isPlaying()) {
            //mediaPlayer.seekTo(0);
            mediaPlayer.start();

        }
    }

    public static void init(Context context) {
        if (mySignal == null) {
            mySignal = new appsResource(context.getApplicationContext());
        }
    }

    public static appsResource getInstance() {
        return mySignal;
    }


    public void vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(500);
        }
    }

    public void toast(String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                } catch (IllegalStateException ex) {}
            }
        });
    }

    public void cancelVibrator(){
        vibrator.cancel();
    }

    public void releaseResource(){
        mediaPlayer.release();
        vibrator.cancel();
    }

}
