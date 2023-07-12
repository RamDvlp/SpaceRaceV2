package com.example.meteoriterace;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class ScoreEntery {

    public static final String KEY_DATE = "DATE";
    public static final String KEY_SCORE = "SCORE";
    public static final String KEY_ENTERY = "ENTERY";

    private int score;

    private double lat;
    private double lon;



    public double getLat() {
        return lat;
    }

    public ScoreEntery setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public ScoreEntery setLon(double lon) {
        this.lon = lon;
        return this;
    }

    private String theDate;

    public ScoreEntery() {
    }

    public ScoreEntery(int score) {
        this.score = score;

        DateTimeFormatter format;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            format = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");
            theDate = LocalDateTime.now().format(format);
        } else {

                // same thing for older versions.
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            theDate = formatter.format(Calendar.getInstance().getTime());
        }
    }

    public int getScore() {
        return score;
    }

    public String getTheDate() {
        return theDate;
    }

    public ScoreEntery setScore(int score) {
        this.score = score;
        return this;
    }

    public ScoreEntery setTheDate(String theDate) {
        this.theDate = theDate;
        return this;
    }

    @Override
    public String toString() {
        return
                theDate +
        ", score: " + score;
    }
}
