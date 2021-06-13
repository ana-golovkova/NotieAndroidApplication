package com.anastasia.notie;

import android.app.Application;
import android.content.Context;

public class NotieApplication extends Application {
    private static NotieApplication instance;

    public static NotieApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
