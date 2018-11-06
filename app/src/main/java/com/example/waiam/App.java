package com.example.waiam;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class App extends Application {
    public static final String TAG = "App";

    private boolean isNightModeEnabled = false;

    @Override
    public void onCreate(){
        super.onCreate();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.isNightModeEnabled = preferences.getBoolean("nightmode", false);
    }

    public boolean isNightModeEnabled() {
        return isNightModeEnabled;
    }

    public void setNightModeEnabled(boolean isNightModeEnabled){
        this.isNightModeEnabled = isNightModeEnabled;
    }
}
