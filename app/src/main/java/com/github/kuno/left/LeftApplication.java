package com.github.kuno.left;

import com.jakewharton.threetenabp.AndroidThreeTen;

import android.app.Application;

/**
 * Created by kuno on 16/8/27.
 * Copyright Starbucks China
 */

public class LeftApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }
}
