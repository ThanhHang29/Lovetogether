package com.ttth.lovetogether.activities;

import android.app.Application;

import com.mz.ZAndroidSystemDK;

/**
 * Created by HangPC on 5/31/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ZAndroidSystemDK.initApplication(this, getPackageName());
    }
}
