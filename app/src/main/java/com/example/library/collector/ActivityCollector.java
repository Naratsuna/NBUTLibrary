package com.example.library.collector;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C)
 * FileName: ActivityCollector
 * Author: Amamiya
 * Date: 2019/12/27 8:22
 * Description: 活动管理器
 * History:
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
}
