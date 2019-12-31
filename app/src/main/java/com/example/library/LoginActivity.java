package com.example.library;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.library.utils.Utils;
import com.example.library.action.SharePrefAction;
import com.example.library.collector.ActivityCollector;
import com.example.library.entity.User;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;

public class LoginActivity extends BaseActivity{
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.ImmersionStatusBar(this,R.id.view_homepage);
        setContentView(R.layout.activity_login);
        final EditText count = findViewById(R.id.editText_count);
        final EditText pwd = findViewById(R.id.editText_pwd);
        Button login = findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(count.getText().toString())||"".equals(pwd.getText().toString())){
                    Toast.makeText(LoginActivity.this, "账号或者密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    List<User> users = LitePal.select("password")
                            .where("stuId = ?",count.getText().toString())
                            .find(User.class);
                    Log.d(TAG, "onClick: old "+count.getText().toString()+" "+pwd.getText().toString());
                    if(users.size() != 0){
                        Log.d(TAG, "onClick: new "+users.get(0).getPassword());
                        if(users.get(0).getPassword().equals(Utils.toMD5(pwd.getText().toString()))){
                            Map<String,String> map = new HashMap<>();
                            map.put("count",count.getText().toString());
                            map.put("pwd", Utils.toMD5(pwd.getText().toString()));
                            map.put("logged","true");//记录已登录
                            map.put("first","false");//记录是否第一次登录
                            SharePrefAction.write(map);
                            HomepageActivity.start(LoginActivity.this);
                        }else{
                            Toast.makeText(LoginActivity.this, "密码有误！", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "帐号有误！", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.finishAll();
    }
}
