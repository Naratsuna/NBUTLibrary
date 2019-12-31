package com.example.library;

import android.app.Application;
import android.content.Context;

import com.example.library.action.SharePrefAction;

import org.litepal.LitePal;

public class MyApplication extends Application {
    private static Context context;

    public static String getCount() {
        return SharePrefAction.read().get("count");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);
    }
    public static Context getContext(){
        return context;
    }
}
