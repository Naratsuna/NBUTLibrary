package com.example.library.action;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.example.library.MyApplication;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Copyright (C)
 * FileName: PermissionRequestAction
 * Author: Amamiya
 * Date: 2019/12/27 18:26
 * Description: 动态请求权限
 * History:
 */
public class PermissionRequestAction {
    final static private String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };//需要的权限

    public static final int PERMISSION_WRITE = 1;

    public static void checkPermission(){
        List<String> result = new ArrayList<>();//传递被拒绝的权限
        for(String permission:permissions){
            if(ContextCompat.checkSelfPermission(MyApplication.getContext(), permission) != PackageManager.PERMISSION_GRANTED){
                result.add(permission);//传递被拒绝的权限
            }
        }
        if(result.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) MyApplication.getContext(), result.toArray(new String[permissions.length]), PERMISSION_WRITE);
        }
    }
}
