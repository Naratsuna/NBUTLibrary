package com.example.library;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.library.utils.Utils;
import com.example.library.action.InitAction;
import com.example.library.action.SharePrefAction;

public class MainActivity extends BaseActivity {

    private boolean isInit = false;//是否初始化过
    private static final String TAG = "MainActivity";
    //停留的时长
    private static final long DELAY_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.ImmersionStatusBar(this,R.id.view_splash);
        setContentView(R.layout.activity_main);
        InitAction.getDatabase();
        String logged;
        if((logged = SharePrefAction.read().get("logged")) != null && logged.equals("true")){
            //即时为true也要再次检查，防止本地修改，暂不实现
            Log.d(TAG, "onWindowFocusChanged: ");
            start(new Intent(MyApplication.getContext(),HomepageActivity.class));
        }else{
            start(new Intent(MyApplication.getContext(),LoginActivity.class));
        }
    }

    private void start(final Intent intent){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        },DELAY_TIME);
    }
}
