package com.example.library.action;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.library.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SharePrefAction {

    private final static String config = "config";
    private static final String TAG = "SharePrefAction";

    public static boolean isExist(){
        SharedPreferences pref = MyApplication.getContext().getSharedPreferences(config,Context.MODE_PRIVATE);
        Log.d(TAG, "isExist: "+pref);
        return pref != null;//永不为空
    }

    public static void write(Map<String, String> map){
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences(config,Context.MODE_PRIVATE).edit();
        for (String key : map.keySet()) {
            String value = "";
            if(map.get(key) != null){
                value = map.get(key);
            }
            editor.putString(key,value);//key唯一
        }
        editor.apply();
    }

    /**
     * 根据需要的键找到值
     */
    public final static Map<String,String> read(){
        Map map = new HashMap<>();
        SharedPreferences pref = MyApplication.getContext().getSharedPreferences(config,Context.MODE_PRIVATE);
        map = pref.getAll();
        return map;
    }
}
