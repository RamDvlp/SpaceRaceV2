package com.example.meteoriterace;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class App extends Application {

    private boolean isAppForeground;

    @Override
    public void onCreate() {
        super.onCreate();

        appsResource.init(this);

        appsResource.getInstance().startBackgroundMusic();


    }

}